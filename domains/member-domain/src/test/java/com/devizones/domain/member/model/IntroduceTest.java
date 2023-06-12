package com.devizones.domain.member.model;

import com.devizones.domain.member.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import com.devizones.util.RandomStringFactory;

import static com.devizones.domain.member.exception.MemberErrorCode.INTRODUCE_EMPTY;
import static com.devizones.domain.member.exception.MemberErrorCode.INTRODUCE_MAX_LENGTH_OVER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IntroduceTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("빈 자기소개일 경우 MemberException 발생")
    public void create_EmptyIntroduce_ThrowsMemberException(String emptyIntroduce) {
        // given

        // when & then
        assertThatThrownBy(() -> Introduce.builder()
                                          .introduce(emptyIntroduce)
                                          .build())
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", INTRODUCE_EMPTY);
    }

    @Test
    @DisplayName("자기소개 길이 제한 초과일 경우 MemberException 발생")
    public void create_IntroduceLengthExceedsLimit_ThrowsMemberException() {
        // given
        String longIntroduce = RandomStringFactory.generateRandomString(Introduce.MAX_LENGTH + 1);

        // when & then
        assertThatThrownBy(() -> Introduce.builder()
                                          .introduce(longIntroduce)
                                          .build())
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", INTRODUCE_MAX_LENGTH_OVER);
    }

    @Test
    @DisplayName("자기소개 길이 제한 최소일 경우 MemberException 발생")
    public void create_IntroduceLengthExceedsLimit_ThrowsMemberException2() {
        // given
        String longIntroduce = RandomStringFactory.generateRandomString(Introduce.MIN_LENGTH - 1);

        // when & then
        assertThatThrownBy(() -> Introduce.builder()
                                          .introduce(longIntroduce)
                                          .build())
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", INTRODUCE_MAX_LENGTH_OVER);
    }

    @Test
    @DisplayName("길이가 최대 자기소개 수가 만족한 경우 Introduce 객체 생성")
    public void create_MaxLengthIntroduce() {
        // given
        String validIntroduce = RandomStringFactory.generateRandomString(Introduce.MAX_LENGTH);

        // when
        Introduce introduce = Introduce.builder()
                                       .introduce(validIntroduce)
                                       .build();

        // then
        assertThat(introduce)
                .isNotNull()
                .extracting("value")
                .isEqualTo(validIntroduce);
    }

    @Test
    @DisplayName("길이가 최소 자기소개 수가 만족한 경우 Introduce 객체 생성")
    public void create_MinLengthIntroduce() {
        // given
        String validIntroduce = RandomStringFactory.generateRandomString(Introduce.MIN_LENGTH);

        // when
        Introduce introduce = Introduce.builder()
                                       .introduce(validIntroduce)
                                       .build();

        // then
        assertThat(introduce)
                .isNotNull()
                .extracting("value")
                .isEqualTo(validIntroduce);
    }

    @Test
    @DisplayName("유효한 자기소개일 경우 Introduce 객체 생성")
    public void create_ValidIntroduce_CreatesIntroduceObject() {
        // given
        String validIntroduce = RandomStringFactory.generateRandomString(Introduce.MIN_LENGTH, Introduce.MAX_LENGTH);

        // when
        Introduce introduce = Introduce.builder()
                                       .introduce(validIntroduce)
                                       .build();

        // then
        assertThat(introduce)
                .isNotNull()
                .extracting("value")
                .isEqualTo(validIntroduce);
    }
}