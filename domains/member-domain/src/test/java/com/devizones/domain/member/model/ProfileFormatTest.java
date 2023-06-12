package com.devizones.domain.member.model;

import com.devizones.domain.member.exception.MemberException;
import com.devizones.domain.member.model.ProfileFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static com.devizones.domain.member.exception.MemberErrorCode.PROFILE_EMPTY;
import static com.devizones.domain.member.exception.MemberErrorCode.PROFILE_NOT_SUPPORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileFormatTest {


    // TODO: Easy-Random 사용
    @DisplayName("유효한 포맷일 경우 올바른 포맷 반환")
    @ParameterizedTest
    @CsvSource(value = {
            "example.png, PNG",
            "example.JPG, JPG",
            "example.jpeg, JPEG",
    })
    public void validateAndExtractFormat_ValidFormat(String fileName, ProfileFormat profileFormat) {
        ProfileFormat format = ProfileFormat.validateAndExtractFormat(fileName);
        assertThat(format).isEqualByComparingTo(profileFormat);
    }

    @Test
    @DisplayName("유효하지 않은 포맷일 경우 MemberException 발생")
    public void validateAndExtractFormat_InvalidFormat() {
        String fileName = "example.docx";
        assertThatThrownBy(() -> ProfileFormat.validateAndExtractFormat(fileName))
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", PROFILE_NOT_SUPPORT);
    }

    @DisplayName("빈 파일 이름일 경우 MemberException 발생")
    @ParameterizedTest
    @NullAndEmptySource
    public void validateAndExtractFormat_EmptyFileName_NullFileName(String emptyFileName) {
        assertThatThrownBy(() -> ProfileFormat.validateAndExtractFormat(emptyFileName))
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", PROFILE_EMPTY);
    }

}