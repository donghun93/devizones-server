package com.devizones.application.member.exception;

import com.devizones.commons.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberServiceErrorCode implements ErrorCode {
    MEMBER_DUPLICATE("회원이 이미 존재합니다."),
    MEMBER_NICKNAME_DUPLICATE("닉네임이 중복되었습니다.")
    ;


    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
