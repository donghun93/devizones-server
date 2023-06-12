package com.devizones.application.post.port;

import com.devizones.application.post.dto.PostDetailDto;
import com.devizones.application.post.dto.PostListDto;
import com.devizones.application.post.dto.PostMyPageUIDto;
import com.devizones.domain.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostQueryPort {
    Post findById(Long postId);
    List<PostListDto> load(Long key, Integer size);
    List<PostListDto> load(Long key, Integer size, String keyword);
    PostDetailDto findByIdDetailDto(Long postId);
    Page<PostMyPageUIDto> getWrittenPost(Pageable pageable, Long writerId);
    Page<PostMyPageUIDto> likePost(Pageable pageable, Long writerId);
}
