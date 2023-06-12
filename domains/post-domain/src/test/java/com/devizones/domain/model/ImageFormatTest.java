package com.devizones.domain.model;

import com.devizones.domain.post.exception.PostException;
import com.devizones.domain.post.model.ImageFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.devizones.domain.post.exception.PostErrorCode.IMAGE_EMPTY;
import static com.devizones.domain.post.exception.PostErrorCode.IMAGE_NOT_SUPPORT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageFormatTest {

    @Test
    @DisplayName("유효한 확장자일 경우 올바른 형식을 반환하는지 테스트합니다")
    void testValidateAndExtractFormat_ValidExtension() {
        // given
        String validFileName = "image.png";

        // when
        ImageFormat format = ImageFormat.validateAndExtractFormat(validFileName);

        // then
        assertEquals(ImageFormat.PNG, format);
    }

    @Test
    @DisplayName("유효하지 않은 확장자일 경우 PostException이 발생하는지 테스트합니다")
    void testValidateAndExtractFormat_InvalidExtension() {
        // given
        String invalidFileName = "image.bmp";

        // when, then
        assertThatThrownBy(() ->
                ImageFormat.validateAndExtractFormat(invalidFileName)
        )
                .isInstanceOf(PostException.class)
                .hasFieldOrPropertyWithValue("errorCode", IMAGE_NOT_SUPPORT);
    }

    @DisplayName("파일 이름이 null인 경우 null을 반환하는지 테스트합니다")
    @ParameterizedTest
    @NullAndEmptySource
    void testValidateAndExtractFormat_NullFileName(String nullFileName) {
        // given

        // when & then
        assertThatThrownBy(() ->
                ImageFormat.validateAndExtractFormat(nullFileName)
        )
                .isInstanceOf(PostException.class)
                .hasFieldOrPropertyWithValue("errorCode", IMAGE_EMPTY);
    }
}