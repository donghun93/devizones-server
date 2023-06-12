package com.devizones.member.rds.exception;

import com.devizones.commons.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberJpaErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND("회원이 존재하지 않습니다.")
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
