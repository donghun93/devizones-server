package com.devizones.domain.member.model;

import com.devizones.commons.StringUtils;
import com.devizones.domain.member.exception.MemberException;
import lombok.Builder;
import lombok.Getter;

import static com.devizones.domain.member.exception.MemberErrorCode.INTRODUCE_EMPTY;
import static com.devizones.domain.member.exception.MemberErrorCode.INTRODUCE_MAX_LENGTH_OVER;

@Getter
public class Introduce {
    public final static int MAX_LENGTH = 100;
    public final static int MIN_LENGTH = 1;
    private String value;

    @Builder
    private Introduce(String introduce) {
        setIntroduce(introduce);
    }

    public Introduce update(String introduce) {
        if (!StringUtils.hasText(introduce)) {
            throw new MemberException(INTRODUCE_EMPTY);
        }
        return new Introduce(introduce);
    }

    private void setIntroduce(String introduce) {
        if (StringUtils.hasText(introduce)) {
            if (introduce.length() > MAX_LENGTH) {
                throw new MemberException(
                        String.format("자기소개는 최대 %d까지 입력해주세요", MAX_LENGTH),
                        INTRODUCE_MAX_LENGTH_OVER
                );
            }
        }
        this.value = introduce;
    }


    public static Introduce create(String introduce) {
        return new Introduce(introduce);
    }
}
