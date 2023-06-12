package com.devizones.post.rds.repository;

import com.devizones.application.post.dto.PostDetailDto;
import com.devizones.application.post.dto.PostListDto;
import com.devizones.application.post.dto.PostMyPageUIDto;
import com.devizones.post.rds.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostQueryRepository {
    List<PostListDto> findByPostsCursorBase(Long key, Integer size);
    List<PostListDto> findByPostsCursorBase(Long key, Integer size, String  keyword);
    Optional<PostEntity> findById(Long id);
    Optional<PostDetailDto> findByIdDetailDto(Long postId);
    Optional<PostEntity> findByIdWithOptimisticLock(Long postId);
    Page<PostMyPageUIDto> getWrittenPost(Pageable pageable, Long writerId);
    Page<PostMyPageUIDto> likePost(Pageable pageable, Long writerId);
}
