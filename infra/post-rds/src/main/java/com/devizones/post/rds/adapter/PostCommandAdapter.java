package com.devizones.post.rds.adapter;

import com.devizones.application.post.port.PostCommandPort;
import com.devizones.application.post.port.PostLikeCommandPort;
import com.devizones.domain.like.model.PostLike;
import com.devizones.domain.post.exception.PostException;
import com.devizones.domain.post.model.Post;
import com.devizones.domain.post.model.Tag;
import com.devizones.post.rds.entity.PostEntity;
import com.devizones.post.rds.entity.PostLikeEntity;
import com.devizones.post.rds.entity.TagEntity;
import com.devizones.post.rds.exception.PostJpaErrorCode;
import com.devizones.post.rds.exception.PostJpaException;
import com.devizones.post.rds.mapper.PostLikeMapper;
import com.devizones.post.rds.mapper.PostMapper;
import com.devizones.post.rds.repository.PostQueryRepository;
import com.devizones.post.rds.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.devizones.post.rds.exception.PostJpaErrorCode.POST_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostCommandAdapter implements PostCommandPort {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private final PostQueryRepository postQueryRepository;

    @Override
    public Post save(Post post) {
        PostEntity postEntity = postMapper.toEntity(post);
        return postMapper.toDomainModel(postRepository.save(postEntity));
    }

    @Override
    public void update(Post post) {
        PostEntity postEntity = postRepository.findById(post.getId())
                                              .orElseThrow(() -> new PostJpaException(PostJpaErrorCode.POST_NOT_FOUND));
        postEntity.update(post);
    }

    @Override
    public void delete(Post post) {
        PostEntity postEntity = postRepository.findById(post.getId())
                                              .orElseThrow(() -> new PostJpaException(PostJpaErrorCode.POST_NOT_FOUND));
        postEntity.delete();
    }

    @Override
    public void like(Long postId) {
        PostEntity postEntity = postQueryRepository.findByIdWithOptimisticLock(postId)
                                                   .orElseThrow(() -> new PostJpaException(POST_NOT_FOUND));
        postEntity.like();
    }

//    @Override
//    public void likeSave(Post post) {
//        PostEntity postEntity = postRepository.findById(post.getId())
//                                              .orElseThrow(() -> new PostJpaException(PostJpaErrorCode.POST_NOT_FOUND));
//        log.info("Thread Id:{}, version:{}", Thread.currentThread().getId(), postEntity.getVersion());
//        postEntity.like();
//    }

    @Override
    public void unlike(Long postId) {
        PostEntity postEntity = postQueryRepository.findByIdWithOptimisticLock(postId)
                                                   .orElseThrow(() -> new PostJpaException(POST_NOT_FOUND));
        postEntity.unlike();
    }


}
