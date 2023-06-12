package com.devizones.domain.model;

import com.devizones.domain.post.exception.PostException;
import com.devizones.domain.post.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.devizones.domain.post.exception.PostErrorCode.TAG_NOT_VALID;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TagTest {
    @Test
    @DisplayName("유효하지 않은 태그 ID 또는 태그 이름이 주어진 경우 PostException이 발생하는지 테스트합니다")
    void testConstructor_InvalidTag() {
        // given

        // when, then
        assertThatThrownBy(() -> Tag.builder()
                                    .id(null)
                                    .tagName("")
                                    .build())
                .isInstanceOf(PostException.class)
                .hasFieldOrPropertyWithValue("errorCode", TAG_NOT_VALID);
    }

    @Test
    @DisplayName("유효한 태그 ID와 태그 이름이 주어진 경우 태그가 생성되는지 테스트합니다")
    void testConstructor_ValidTag() {
        // given
        Long validId = 1L;
        String validTagName = "example";

        // when
        Tag tag = Tag.builder()
                     .id(validId)
                     .tagName(validTagName)
                     .build();

        // then
        Assertions.assertEquals(validId, tag.getId());
        Assertions.assertEquals(validTagName, tag.getValue());
    }
}