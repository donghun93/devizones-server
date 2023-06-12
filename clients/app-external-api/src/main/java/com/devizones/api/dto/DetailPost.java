package com.devizones.api.dto;

import com.devizones.application.post.dto.PostDetailDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class DetailPost {
    @Getter
    public static class Response {
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

        public Response(Long postId, Long writerId, String title, String contents, LocalDateTime createdAt, Long likeCount, List<String> tags, String nickname, String profile, boolean write, boolean like) {
            this.postId = postId;
            this.writerId = writerId;
            this.title = title;
            this.contents = contents;
            this.createdAt = createdAt;
            this.likeCount = likeCount;
            this.tags = tags;
            this.nickname = nickname;
            this.profile = profile;
            this.write = write;
            this.like = like;
        }

        public static BaseResponse<DetailPost.Response> success(PostDetailDto postDetailDto, String profileS3) {
            return BaseResponse.success(new DetailPost.Response(
                    postDetailDto.getPostId(),
                    postDetailDto.getWriterId(),
                    postDetailDto.getTitle(),
                    postDetailDto.getContents(),
                    postDetailDto.getCreatedAt(),
                    postDetailDto.getLikeCount(),
                    postDetailDto.getTags(),
                    postDetailDto.getNickname(),
                    profileS3 + postDetailDto.getProfile(),
                    postDetailDto.isWrite(),
                    postDetailDto.isLike()
            ));
        }
    }
}
