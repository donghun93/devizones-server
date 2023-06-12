package com.devizones.domain.member.model;

import com.devizones.domain.member.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import com.devizones.util.RandomStringFactory;

import static com.devizones.domain.member.exception.MemberErrorCode.NICKNAME_EMPTY;
import static com.devizones.domain.member.exception.MemberErrorCode.NICKNAME_FORMAT_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NicknameTest {

    @ParameterizedTest(name = "닉네임이 null인 경우 예외 발생 테스트")
    @NullAndEmptySource
    public void createNicknameWithNullAndEmpty(String emptyNickname) {
        // given

        // when & then
        assertThatThrownBy(() -> Nickname.builder()
                                         .nickname(emptyNickname)
                                         .build())
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", NICKNAME_EMPTY);
    }

    // TODO: 메서드명 수정
    @Test
    @DisplayName("닉네임 길이가 최소 길이인 경우 예외 발생 테스트")
    public void test1() {
        // given
        String nickName = RandomStringFactory.generateRandomString(Nickname.MIN_LENGTH - 1);

        // when & then
        assertThatThrownBy(() -> Nickname.builder()
                                         .nickname(nickName)
                                         .build())
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", NICKNAME_FORMAT_ERROR);
    }


    @Test
    @DisplayName("닉네임 길이가 최대 길이인 경우 예외 발생 테스트")
    public void test2() {
        // given
        String nickName = RandomStringFactory.generateRandomString(Nickname.MAX_LENGTH + 1);

        // when & then
        assertThatThrownBy(() -> Nickname.builder()
                                         .nickname(nickName)
                                         .build())
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", NICKNAME_FORMAT_ERROR);
    }

    @Test
    @DisplayName("닉네임 길이가 최대 길이인 경우 테스트")
    public void test3() {
        // given
        String nickName = RandomStringFactory.generateRandomString(Nickname.MAX_LENGTH);

        // when
        Nickname nickname = Nickname.builder()
                                    .nickname(nickName)
                                    .build();

        // then
        assertThat(nickname)
                .isNotNull()
                .extracting("value")
                .isEqualTo(nickName);
    }

    @Test
    @DisplayName("닉네임 길이가 최소 길이인 경우 테스트")
    public void test4() {
        // given
        String nickName = RandomStringFactory.generateRandomString(Nickname.MIN_LENGTH);

        // when
        Nickname nickname = Nickname.builder()
                                    .nickname(nickName)
                                    .build();

        // then
        assertThat(nickname)
                .isNotNull()
                .extracting("value")
                .isEqualTo(nickName);
    }

    @Test
    @DisplayName("유효한 닉네임 생성 테스트")
    public void createValidNickname() {
        // given
        String validNickname = RandomStringFactory.generateRandomString(Nickname.MIN_LENGTH, Nickname.MAX_LENGTH);

        // when
        Nickname nickname = Nickname.builder()
                                    .nickname(validNickname)
                                    .build();

        // then
        assertThat(nickname)
                .isNotNull()
                .extracting("value")
                .isEqualTo(validNickname);
    }
}