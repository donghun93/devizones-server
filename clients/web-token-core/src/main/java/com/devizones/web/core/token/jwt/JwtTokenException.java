package com.devizones.web.core.token.jwt;

import lombok.Getter;

@Getter
public class JwtTokenException extends RuntimeException {
    private final JwtTokenErrorCode memberErrorCode;

    public JwtTokenException(JwtTokenErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.memberErrorCode = errorCode;
    }
}