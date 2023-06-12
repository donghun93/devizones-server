package com.devizones.domain.member.model;

import com.devizones.domain.member.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import com.devizones.util.RandomEmailStringFactory;

import static com.devizones.domain.member.exception.MemberErrorCode.EMAIL_EMPTY;
import static com.devizones.domain.member.exception.MemberErrorCode.EMAIL_FORMAT_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @DisplayName("이메일 생성 시 유효성 검사를 한다.")
    @Test
    public void createValidEmail() {
        // given
        String validEmail = RandomEmailStringFactory.getRandomStringEmail();

        // when
        Email email = Email.builder()
                           .email(validEmail)
                           .build();

        // then
        assertThat(email)
                .isNotNull()
                .extracting("value")
                .isEqualTo(validEmail);
    }

    @ParameterizedTest(name = "이메일 생성 시 빈 문자열 또는 null 인 경우 예외를 반환한다.")
    @NullAndEmptySource
    public void createEmptyEmail(String emptyEmail) {
        // given

        // when & then
        assertThatThrownBy(() -> Email.builder()
                                      .email(emptyEmail)
                                      .build())
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", EMAIL_EMPTY);
    }

    @DisplayName("이메일 생성 시 이메일 형식이 맞지 않는경우 예외를 반환한다.")
    @Test
    public void createInvalidFormatEmail() {
        //given
        String invalidEmail = RandomEmailStringFactory.getRandomStringInvalidEmail();

        // when & then
        assertThatThrownBy(() -> Email.builder()
                                      .email(invalidEmail)
                                      .build())
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", EMAIL_FORMAT_ERROR);
    }

    @Test
    public void createLongEmail() {
        // given
        String longEmail = RandomEmailStringFactory.getRandomStringEmail(1, 100);

        // when
        Email email = Email.builder()
                           .email(longEmail)
                           .build();

        // then
        assertThat(email)
                .isNotNull()
                .extracting("value")
                .isEqualTo(longEmail);
    }

}