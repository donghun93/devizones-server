package com.devizones.web.core.token.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtTokenErrorCode {
    JWT_INVALID_TOKEN("Invalid JWT Token"),
    JWT_ACCESS_TOKEN_EXPIRED("Access Token이 만료되었습니다."),
    JWT_UNSUPPORTED_TOKEN("Unsupported JWT Token"),
    JWT_CLAIMS_EMPTY("JWT claims string is empty."),
    JWT_ACCESS_TOKEN_NOT_EXIST("JWT Access Token이 존재하지 않습니다."),
    JWT_REFRESH_TOKEN_NOT_EXIST("JWT Refresh Token이 존재하지 않습니다."),
    JWT_REFRESH_TOKEN_NOT_SAME("JWT Refresh 토큰이 일치하지 않습니다."),
    JWT_ACCESS_TOKEN_MALFORMED_ERROR("잘못된 JWT Access Token 서명입니다."),
    JWT_REFRESH_TOKEN_MALFORMED_ERROR("잘못된 JWT Refresh Token 서명입니다."),
    JWT_REFRESH_TOKEN_NOT_VALID("Refresh Token과 현재 접속한 사용자의 정보가 일치하지 않습니다."),
    JWT_MEMBER_NOT_FOUND("유효하지 않는 JWT Access Token입니다.")
    ;

    private final String errorMessage;
}