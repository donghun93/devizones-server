package com.devizones.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ListSearchPost {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListSearchPostRequest {
        @Schema(
                description = "요청 키 값",
                example = "-1",
                defaultValue = "-1"
        )
        private Long key;

        @Schema(
                description = "요청 사이즈",
                example = "10",
                defaultValue = "10"
        )
        private Integer size;

        @Schema(
                description = "검색어",
                example = "검색어",
                defaultValue = "검색어"
        )
        @NotBlank(message = "keyword는 필수입니다.")
        private String keyword;
    }
}
