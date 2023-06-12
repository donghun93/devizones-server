package com.devizones.redis.member.exception;

import com.devizones.commons.ErrorCode;
import lombok.Getter;

@Getter
public class MemberRedisException extends RuntimeException {
    private final ErrorCode errorCode;

    public MemberRedisException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
