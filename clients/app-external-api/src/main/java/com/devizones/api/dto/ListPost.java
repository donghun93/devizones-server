package com.devizones.api.dto;

import com.devizones.application.post.dto.ListPostQueryResultDto;
import com.devizones.application.post.dto.PostListViewDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ListPost {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListPostRequest {
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
    }

    @Getter
    public static class Response {
        private final CursorResponse nextCursor;
        private final List<ListPostItem> items;

        public Response(CursorResponse nextCursor, List<ListPostItem> items) {
            this.nextCursor = nextCursor;
            this.items = items;
        }

        public static BaseResponse<ListPost.Response> success(ListPostQueryResultDto listPostQueryResultDto, String profileS3, String thumbnailS3) {
            return BaseResponse.success(new ListPost.Response(
                            new CursorResponse(listPostQueryResultDto.key()),
                            listPostQueryResultDto.items()
                                                  .stream()
                                                  .map(i -> ListPostItem.success(i, profileS3, thumbnailS3))
                                                  .collect(Collectors.toList())
                    )
            );
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CursorResponse {
        private Long key;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ListPostItem {
        private Long postId;
        private Long writerId;
        private String title;
        private String summary;
        private LocalDateTime createdAt;
        private String nickname;
        private String profile;
        private String thumbnail;
        private Long like;

        public static ListPostItem success(PostListViewDto postItemDto, String profileS3, String thumbnailS3) {
            return new ListPostItem(
                    postItemDto.postId(),
                    postItemDto.writerId(),
                    postItemDto.title(),
                    postItemDto.summary(),
                    postItemDto.createdAt(),
                    postItemDto.nickname(),
                    profileS3 + postItemDto.profile(),
                    StringUtils.hasText(postItemDto.thumbNail()) ? thumbnailS3 + postItemDto.thumbNail() : null,
                    postItemDto.like()
            );
        }
    }
}
