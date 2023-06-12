package com.devizones.api.dto;

import com.devizones.application.post.dto.PostCreateCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CreatePost {
    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "제목은 필수값 입니다.")
        private String title;


        @NotBlank(message = "본문은 필수값 입니다.")
        private String contents;

        private List<String> tags;

        private boolean visible = true;

        private String summary;


        private MultipartFile thumbnail;
        public PostCreateCommand toCommand() {

            InputStream inputStream = null;
            try {
                if(thumbnail != null) {
                    inputStream = thumbnail.getInputStream();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return new PostCreateCommand(
                    title,
                    contents,
                    tags,
                    visible,
                    (thumbnail == null) ? null : thumbnail.getOriginalFilename(),
                    (thumbnail == null) ? null : inputStream,
                    summary
            );
        }
    }

    @Getter
    public static class Response {
        private final Long postId;

        private Response(Long postId) {
            this.postId = postId;
        }

        public static BaseResponse<CreatePost.Response> success(Long postId) {
            return BaseResponse.success(new Response(postId));
        }

    }
}
