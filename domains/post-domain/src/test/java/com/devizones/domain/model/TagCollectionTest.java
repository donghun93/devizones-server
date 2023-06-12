package com.devizones.domain.model;

import com.devizones.domain.post.exception.PostException;
import com.devizones.domain.post.model.Tag;
import com.devizones.domain.post.model.TagCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.devizones.domain.post.exception.PostErrorCode.TAG_DUPLICATE;
import static com.devizones.domain.post.exception.PostErrorCode.TAG_MAX_SIZE_OVER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

class TagCollectionTest {
    @Test
    @DisplayName("태그 컬렉션의 크기가 최대 크기를 초과하는 경우 PostException이 발생하는지 테스트합니다")
    void testCreate_MaxSizeExceeded() {
        // given
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < TagCollection.MAX_SIZE + 1; i++) {
            tags.add(Tag.builder()
                        .id((long) (i + 1))
                        .tagName("tag" + i)
                        .build());
        }

        // when, then
        assertThatThrownBy(() -> TagCollection.create(tags))
                .isInstanceOf(PostException.class)
                .hasFieldOrPropertyWithValue("errorCode", TAG_MAX_SIZE_OVER);
    }

    @Test
    @DisplayName("태그 컬렉션에 중복된 태그가 있는 경우 PostException이 발생하는지 테스트합니다")
    void testCreate_DuplicateTag() {
        // given
        Tag tag1 = Tag.builder()
                      .id(1L)
                      .tagName("tag1")
                      .build();

        Tag tag2 = Tag.builder()
                      .id(2L)
                      .tagName("tag2")
                      .build();

        Tag tag3 = Tag.builder()
                      .id(1L)
                      .tagName("tag1")
                      .build();
        List<Tag> tags = Arrays.asList(tag1, tag2, tag3);

        // when, then
        assertThatThrownBy(() -> TagCollection.create(tags))
                .isInstanceOf(PostException.class)
                .hasFieldOrPropertyWithValue("errorCode", TAG_DUPLICATE);
    }


    @Test
    @DisplayName("태그 컬렉션에 중복된 태그가 있는 경우 PostException이 발생하는지 테스트합니다")
    void testCreate_DuplicateTag2() {
        // given
        Tag tag1 = Tag.builder()
                      .id(1L)
                      .tagName("tag1")
                      .build();

        Tag tag2 = Tag.builder()
                      .id(2L)
                      .tagName("tag2")
                      .build();

        Tag tag3 = Tag.builder()
                      .id(3L)
                      .tagName("tag1")
                      .build();
        List<Tag> tags = Arrays.asList(tag1, tag2, tag3);

        // when, then
        assertThatThrownBy(() -> TagCollection.create(tags))
                .isInstanceOf(PostException.class)
                .hasFieldOrPropertyWithValue("errorCode", TAG_DUPLICATE);
    }

    @Test
    @DisplayName("태그 컬렉션에 중복된 태그가 있는 경우 PostException이 발생하는지 테스트합니다")
    void testCreate_DuplicateTag3() {
        // given
        Tag tag1 = Tag.builder()
                      .id(1L)
                      .tagName("tag1")
                      .build();

        Tag tag2 = Tag.builder()
                      .id(2L)
                      .tagName("tag2")
                      .build();

        Tag tag3 = Tag.builder()
                      .id(1L)
                      .tagName("tag3")
                      .build();
        List<Tag> tags = Arrays.asList(tag1, tag2, tag3);

        // when, then
        assertThatThrownBy(() -> TagCollection.create(tags))
                .isInstanceOf(PostException.class)
                .hasFieldOrPropertyWithValue("errorCode", TAG_DUPLICATE);
    }


    @Test
    @DisplayName("유효한 태그 컬렉션이 주어진 경우 태그 컬렉션이 생성되는지 테스트합니다")
    void testCreate_ValidTagCollection() {
        // given
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < TagCollection.MAX_SIZE; i++) {
            tags.add(Tag.builder()
                        .id((long) (i + 1))
                        .tagName("tag" + i)
                        .build());
        }

        // when
        TagCollection tagCollection = TagCollection.create(tags);

        // then
        assertThat(tagCollection.getTags())
                .isNotNull()
                .isNotEmpty()
                .extracting("id", "value")
                .contains(
                        tuple(1L, "tag0"),
                        tuple(2L, "tag1"),
                        tuple(3L, "tag2"),
                        tuple(4L, "tag3"),
                        tuple(5L, "tag4")
                );
    }
}