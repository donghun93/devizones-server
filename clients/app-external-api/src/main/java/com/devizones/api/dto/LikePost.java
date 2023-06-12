package com.devizones.api.dto;

import lombok.Getter;

public class LikePost {
    @Getter
    public static class LikeResponse {
        private Long postId;

        public LikeResponse(Long postId) {
            this.postId = postId;
        }

        public static BaseResponse<LikePost.LikeResponse> success(Long postId) {
            return BaseResponse.success(new LikePost.LikeResponse(postId));
        }
    }

    @Getter
    public static class UnLikeResponse {
        private Long postId;

        public UnLikeResponse(Long postId) {
            this.postId = postId;
        }

        public static BaseResponse<LikePost.UnLikeResponse> success(Long postId) {
            return BaseResponse.success(new LikePost.UnLikeResponse(postId));
        }
    }
}
