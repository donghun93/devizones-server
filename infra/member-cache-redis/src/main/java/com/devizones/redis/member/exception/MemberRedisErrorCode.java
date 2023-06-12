package com.devizones.redis.member.exception;

import com.devizones.commons.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberRedisErrorCode implements ErrorCode {
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
