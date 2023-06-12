package com.devizones.application.post.dto;

import com.devizones.domain.member.model.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetailDto {
    private Long postId;
    private Long writerId;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private Long likeCount;
    private List<String> tags;
    private String nickname;
    private String profile;
    private boolean write;
    private boolean like;

    public PostDetailDto(Long postId, Long writerId, String title, String contents, LocalDateTime createdAt, Long likeCount) {
        this.postId = postId;
        this.writerId = writerId;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setMember(Member member) {
        this.nickname = member.getNickname().getValue();
        this.profile = member.getProfile().getFileName();
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
