package com.devizones.application.post.dto;

import java.time.LocalDateTime;

public record PostListDto(
        Long postId,
        Long writerId,
        String title,
        String summary,
        LocalDateTime createdAt,
        Long like,
        String thumbNail
) {
}
