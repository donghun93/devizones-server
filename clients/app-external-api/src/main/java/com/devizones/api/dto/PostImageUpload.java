package com.devizones.api.dto;

import lombok.Getter;

public class PostImageUpload {
    @Getter
    public static class Response {
        private final String path;

        private Response(String path) {
            this.path = path;
        }

        public static BaseResponse<PostImageUpload.Response> success(String path, String s3) {
            return BaseResponse.success(new Response(s3 + path));
        }
    }

}
