package com.devizones.api.dto;

import com.devizones.application.post.dto.PostEditCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EditPost {
    @Getter
    @Setter
    public static class Request {
        @NotBlank(message = "title은 필수입니다.")
        private String title;

        @NotBlank(message = "contents은 필수입니다.")
        private String contents;

        private List<String> tags;

        private boolean visible = true;

        private String summary;

        private MultipartFile thumbnail;

        public PostEditCommand toCommand() {

            InputStream inputStream = null;

            if(thumbnail != null) {
                try {
                    inputStream = thumbnail.getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return new PostEditCommand(
                    title,
                    contents,
                    tags,
                    visible,
                    summary,
                    (thumbnail != null) ? thumbnail.getOriginalFilename() : null,
                    (thumbnail != null) ? inputStream : null
            );
        }
    }

    @Getter
    public static class Response {
        private final Long postId;

        private Response(Long postId) {
            this.postId = postId;
        }

        public static BaseResponse<EditPost.Response> success(Long postId) {
            return BaseResponse.success(new EditPost.Response(postId));
        }

    }
}
