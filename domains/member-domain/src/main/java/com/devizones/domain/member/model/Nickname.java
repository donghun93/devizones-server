package com.devizones.domain.member.model;

import com.devizones.commons.StringUtils;
import com.devizones.domain.member.exception.MemberErrorCode;
import com.devizones.domain.member.exception.MemberException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Nickname {

    public final static int MIN_LENGTH = 2;
    public final static int MAX_LENGTH = 10;
    private String value;

    @Builder
    private Nickname(String nickname) {
        setNickname(nickname);
    }

    private void setNickname(String nickname) {
        if (!StringUtils.hasText(nickname)) {
            throw new MemberException(MemberErrorCode.NICKNAME_EMPTY);
        }

        if (nickname.length() < MIN_LENGTH || nickname.length() > MAX_LENGTH) {
            throw new MemberException(
                    String.format("닉네임은 %d에서 %d까지 입력해주세요", MIN_LENGTH, MAX_LENGTH),
                    MemberErrorCode.NICKNAME_FORMAT_ERROR
            );
        }

        this.value = nickname;
    }

    public static Nickname create(String nickname) {
        return new Nickname(nickname);
    }

    public Nickname update(String nickName) {
        return new Nickname(nickName);
    }
}
