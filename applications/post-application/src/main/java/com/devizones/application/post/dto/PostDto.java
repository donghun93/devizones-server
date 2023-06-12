package com.devizones.application.post.dto;

import com.devizones.domain.post.model.Post;

public record PostDto(
        Long postId
) {

    public static PostDto of(Post post) {
        return new PostDto(post.getId());
    }
}
