package com.devizones.application.post.service;

import com.devizones.application.post.config.ConcurrencyProperties;
import com.devizones.application.post.dto.PostCreateCommand;
import com.devizones.application.post.dto.PostDto;
import com.devizones.application.post.dto.PostEditCommand;
import com.devizones.application.post.port.*;
import com.devizones.commons.StringUtils;
import com.devizones.domain.like.model.PostLike;
import com.devizones.domain.post.dto.CreatePostDomainModelDto;
import com.devizones.domain.post.dto.EditPostDomainModelDto;
import com.devizones.domain.post.exception.PostException;
import com.devizones.domain.post.model.Post;
import com.devizones.domain.post.model.Tag;
import com.devizones.domain.post.service.RandomFileNameGenerateDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

import static com.devizones.application.post.exception.PostServiceErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostCommandService {

    private final PostQueryPort postQueryPort;
    private final PostCommandPort postCommandPort;
    private final PostLikeQueryPort postLikeQueryPort;
    private final ConcurrencyProperties concurrencyProperties;
    private final TagFacadeService tagFacadeService;
    private final ThumbnailImagePort thumbnailImagePort;
    private final PostLikeFacade postLikeFacade;

    // 게시글 생성
    @Transactional
    public Long register(Long writerId, PostCreateCommand command) {
        // TODO: AWS EC2에 올라간 불필요한 이미지 삭제해야한다. 배치로 처리할 예정

        List<Tag> tags = tagFacadeService.createOrFind(command.tags());

        CreatePostDomainModelDto createPostDomainModelDto = command.toDomainModelDto(writerId, tags);
        Post post = Post.create(createPostDomainModelDto);

        if (StringUtils.hasText(command.thumbnailFilename())) {
            thumbnailImagePort.upload(post.getThumbnail(), command.thumbnail());
        }
        return postCommandPort.save(post)
                              .getId();
    }

    // 게시글 수정
    @Transactional
    public void edit(Long postId, Long editMemberId, PostEditCommand command) {

        Post post = postQueryPort.findById(postId);

        if(!post.getWriterId().equals(editMemberId)) {
            throw new PostException(POST_WRITER_NOT_MATCH);
        }

        List<Tag> tags = tagFacadeService.createOrFind(command.tags());
        EditPostDomainModelDto editPostDomainModelDto = command.toDomainModelDto(tags);
        post.edit(editPostDomainModelDto);
        postCommandPort.update(post);

        if (StringUtils.hasText(command.thumbnailFilename())) {
            thumbnailImagePort.upload(post.getThumbnail(), command.thumbnail());
        }
    }

    // 게시글 삭제
    @Transactional
    public Long delete(Long postId, Long writerId) {
        Post post = findById(postId);
        post.delete(writerId);
        postCommandPort.delete(post);
        return post.getId();
    }

    // 게시글 좋아요
    public void like(Long postId, Long memberId) {

        // 여기서는 Post Like 테이블에 데이터가 있으면 에러를 발생시킨다.
        if (postLikeQueryPort.existByPostIdAndMemberId(postId, memberId)) {
            throw new PostException(POST_LIKE_ALREADY);
        }

        while (true) {
            try {
                postLikeFacade.like(postId, memberId);
                break;
            } catch (OptimisticLockingFailureException e) {
                try {
                    Thread.sleep(concurrencyProperties.getWaitTime());
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    // 게시글 좋아요 해제
    public void unLike(Long postId, Long memberId) {

        if (!postLikeQueryPort.existByPostIdAndMemberId(postId, memberId)) {
            throw new PostException(POST_LIKE_NOT_EXIST);
        }

        while (true) {
            try {
                postLikeFacade.unlike(postId, memberId);

                break;
            } catch (OptimisticLockingFailureException e) {
                try {
                    Thread.sleep(concurrencyProperties.getWaitTime());
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private Post findById(Long postId) {
        return postQueryPort.findById(postId);
    }

    public String upload(String filename, InputStream inputStream) {

        String randomFileName = RandomFileNameGenerateDomainService.generate(filename);
        thumbnailImagePort.upload(randomFileName, inputStream);

        return randomFileName;
    }
}
