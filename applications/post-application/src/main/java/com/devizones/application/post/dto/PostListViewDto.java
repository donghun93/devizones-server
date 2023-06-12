package com.devizones.application.post.dto;

import java.time.LocalDateTime;

public record PostListViewDto(
        Long postId,
        Long writerId,
        String title,
        String summary,
        LocalDateTime createdAt,
        String nickname,
        String profile,
        String thumbNail,
        Long like
) {
}
