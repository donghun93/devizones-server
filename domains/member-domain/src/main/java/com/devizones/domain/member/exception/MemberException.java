package com.devizones.domain.member.exception;

import com.devizones.commons.ErrorCode;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {
    private final ErrorCode errorCode;

    public MemberException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MemberException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
