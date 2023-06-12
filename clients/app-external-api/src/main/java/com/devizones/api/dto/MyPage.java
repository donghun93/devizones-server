package com.devizones.api.dto;

import com.devizones.application.post.dto.PostMyPageUIDto;
import com.devizones.commons.StringUtils;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class MyPage {
    public static class Written {
        @Getter
        public static class Response {

            private Long postId;
            private Long writerId;
            private String title;
            private String thumbnail;
            private String summary;
            private LocalDateTime createdAt;
            private LocalDateTime modifiedAt;
            private Long likeCount;
            private boolean visible;

            private Response(PostMyPageUIDto dto, String s3) {
                this.postId = dto.getPostId();
                this.writerId = dto.getWriterId();
                this.title = dto.getTitle();
                this.thumbnail = (StringUtils.hasText(dto.getThumbnail())) ? s3 + dto.getThumbnail() : null;
                this.summary = dto.getSummary();
                this.createdAt = dto.getCreatedAt();
                this.modifiedAt = dto.getModifiedAt();
                this.likeCount = dto.getLikeCount();
                this.visible = dto.isVisible();
            }

            public static BaseResponse<Page<MyPage.Written.Response>> success(Page<PostMyPageUIDto> myPageUIDto, String s3) {
                return BaseResponse.success(myPageUIDto.map(d -> new Response(d, s3)));
            }
        }
    }
}
