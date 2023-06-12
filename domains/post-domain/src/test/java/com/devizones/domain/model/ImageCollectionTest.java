package com.devizones.domain.model;

import com.devizones.domain.post.model.Image;
import com.devizones.domain.post.model.ImageCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class ImageCollectionTest {
    @Test
    @DisplayName("썸네일이 있는 경우 이미지가 추가되고, 첫 번째 이미지가 썸네일로 설정되는지 테스트합니다")
    void testCreate_WithThumbnail() {
        // given
        String contents = "### 본문입니다.\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/1bec85c6d-3185-462c-b81d-ace7d795b7dd.png)" + "'\n" +
                "abcdefg\n" +
                "### 제목입니다.\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/2bec85c6d-3185-462c-b81d-ace7d795b7dd.png)\n" +
                "---\n" +
                "가나다라마바사";
        String thumbnail = "example-thumbnail.jpg";

        // when
        ImageCollection collection = ImageCollection.create(contents, thumbnail);
        List<Image> images = collection.getImages();

        // then
        assertThat(images)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .extracting("path", "thumbnail")
                .contains(
                        tuple("1bec85c6d-3185-462c-b81d-ace7d795b7dd.png", false),
                        tuple("2bec85c6d-3185-462c-b81d-ace7d795b7dd.png", false),
                        tuple("example-thumbnail.jpg", true)
                );
    }

    @Test
    @DisplayName("썸네일이 없는 경우 이미지가 추가되지만, 첫 번째 이미지가 썸네일로 설정되는지 테스트합니다")
    void testCreate_WithoutThumbnail() {
        // given
        String contents = "### 본문입니다.\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/1bec85c6d-3185-462c-b81d-ace7d795b7dd.png)" + "'\n" +
                "abcdefg\n" +
                "### 제목입니다.\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/2bec85c6d-3185-462c-b81d-ace7d795b7dd.png)\n" +
                "---\n" +
                "가나다라마바사";

        // when
        ImageCollection collection = ImageCollection.create(contents, null);
        List<Image> images = collection.getImages();

        // then
        assertThat(images)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .extracting("path", "thumbnail")
                .contains(
                        tuple("1bec85c6d-3185-462c-b81d-ace7d795b7dd.png", true),
                        tuple("2bec85c6d-3185-462c-b81d-ace7d795b7dd.png", false)
                );
    }

    @DisplayName("내용이 없는 경우 빈 이미지 컬렉션이 반환되는지 테스트합니다")
    @ParameterizedTest
    @NullAndEmptySource
    void testCreate_EmptyContents(String emptyContents) {
        // given
        String thumbnail = "example-thumbnail.jpg";

        // when
        ImageCollection collection = ImageCollection.create(emptyContents, thumbnail);
        List<Image> images = collection.getImages();

        // then
        assertThat(images)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .extracting("path", "thumbnail")
                .contains(
                        tuple("example-thumbnail.jpg", true)
                );
    }

    @DisplayName("내용이 없는 경우 빈 이미지 컬렉션이 반환되는지 테스트합니다")
    @Test
    void testCreate_NotExistImageContents() {
        // given
        String contents =  "### 본문입니다.\n" +
                "abcdefg\n" +
                "### 제목입니다.\n" +
                "---\n" +
                "가나다라마바사";
        String thumbnail = "example-thumbnail.jpg";

        // when
        ImageCollection collection = ImageCollection.create(contents, thumbnail);
        List<Image> images = collection.getImages();

        // then
        assertThat(images)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .extracting("path", "thumbnail")
                .contains(
                        tuple("example-thumbnail.jpg", true)
                );
    }

    @DisplayName("내용이 없는 경우 빈 이미지 컬렉션이 반환되는지 테스트합니다")
    @ParameterizedTest
    @NullAndEmptySource
    void testCreate_NotExistImageContents_EmptyThumbnail(String emptyThumbnail) {
        // given
        String contents =  "### 본문입니다.\n" +
                "abcdefg\n" +
                "### 제목입니다.\n" +
                "---\n" +
                "가나다라마바사";

        // when
        ImageCollection collection = ImageCollection.create(contents, emptyThumbnail);
        List<Image> images = collection.getImages();

        // then
        assertThat(images)
                .isNotNull()
                .isEmpty();
    }
}