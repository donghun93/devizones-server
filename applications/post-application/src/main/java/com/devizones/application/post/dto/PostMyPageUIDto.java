package com.devizones.application.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostMyPageUIDto {
    private Long postId;
    private Long writerId;
    private String title;
    private String thumbnail;
    private String summary;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long likeCount;
    private boolean visible;

    public PostMyPageUIDto(Long postId, Long writerId, String title, String thumbnail, String summary, LocalDateTime createdAt, LocalDateTime modifiedAt, Long likeCount, boolean visible) {
        this.postId = postId;
        this.writerId = writerId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.summary = summary;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.likeCount = likeCount;
        this.visible = visible;
    }
}
