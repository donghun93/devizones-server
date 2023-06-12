package com.devizones.domain.service;

import com.devizones.domain.post.model.Image;
import com.devizones.domain.post.model.ImageFormat;
import com.devizones.domain.post.service.ContentsImageAnalyzeDomainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class ContentsImageAnalyzeDomainServiceTest {

    @DisplayName("본문에서 이미지 데이터 추출하는 테스트")
    @Test
    void getImages() {
        // given
        String contents = "### 본문입니다.\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/1bec85c6d-3185-462c-b81d-ace7d795b7dd.png)" + "'\n" +
                "abcdefg\n" +
                "### 제목입니다.\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/2bec85c6d-3185-462c-b81d-ace7d795b7dd.png)\n" +
                "---\n" +
                "가나다라마바사";

        // when
        List<Image> images = ContentsImageAnalyzeDomainService.getImages(contents);

        // then
        assertThat(images)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .extracting("path", "imageFormat", "thumbnail")
                .contains(
                        tuple("1bec85c6d-3185-462c-b81d-ace7d795b7dd.png", ImageFormat.PNG, false),
                        tuple("2bec85c6d-3185-462c-b81d-ace7d795b7dd.png", ImageFormat.PNG, false)
                );
    }
}