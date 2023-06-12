package com.devizones.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class DeletePost {
    @Getter
    public static class Response {
        private final Long deletePostId;

        public Response(Long deletePostId) {
            this.deletePostId = deletePostId;
        }

        public static BaseResponse<DeletePost.Response> success(Long deletedId) {
            return BaseResponse.success(new Response(deletedId));
        }

    }
}
