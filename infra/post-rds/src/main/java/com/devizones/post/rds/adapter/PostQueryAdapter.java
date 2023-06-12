package com.devizones.post.rds.adapter;

import com.devizones.application.post.dto.PostDetailDto;
import com.devizones.application.post.dto.PostListDto;
import com.devizones.application.post.dto.PostMyPageUIDto;
import com.devizones.application.post.port.PostQueryPort;
import com.devizones.domain.post.model.Post;
import com.devizones.post.rds.entity.PostEntity;
import com.devizones.post.rds.exception.PostJpaException;
import com.devizones.post.rds.mapper.PostMapper;
import com.devizones.post.rds.repository.PostQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.devizones.post.rds.exception.PostJpaErrorCode.POST_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostQueryAdapter implements PostQueryPort {

    private final PostQueryRepository postQueryRepository;
    private final PostMapper postMapper;

    @Override
    public Post findById(Long postId) {
        PostEntity postEntity = postQueryRepository.findById(postId)
                                                   .orElseThrow(() -> new PostJpaException(POST_NOT_FOUND));
        return postMapper.toDomainModel(postEntity);
    }

    @Override
    public List<PostListDto> load(Long key, Integer size) {
        return postQueryRepository.findByPostsCursorBase(key, size);
    }

    @Override
    public List<PostListDto> load(Long key, Integer size, String keyword) {
        return postQueryRepository.findByPostsCursorBase(key, size, keyword);
    }

    @Override
    public PostDetailDto findByIdDetailDto(Long postId) {
        return postQueryRepository.findByIdDetailDto(postId)
                                  .orElseThrow(() -> new PostJpaException(POST_NOT_FOUND));
    }

    @Override
    public Page<PostMyPageUIDto> getWrittenPost(Pageable pageable, Long writerId) {
        return postQueryRepository.getWrittenPost(pageable, writerId);
    }

    @Override
    public Page<PostMyPageUIDto> likePost(Pageable pageable, Long writerId) {
        return postQueryRepository.likePost(pageable, writerId);
    }
}
