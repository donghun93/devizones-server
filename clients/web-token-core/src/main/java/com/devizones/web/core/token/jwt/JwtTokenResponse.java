package com.devizones.web.core.token.jwt;

public record JwtTokenResponse(
        String grantType,
        String accessToken,
        String refreshToken) {

    public static JwtTokenResponse of(JwtTokenDto jwtTokenDto) {
        return new JwtTokenResponse(
                jwtTokenDto.grantType(),
                jwtTokenDto.accessToken(),
                jwtTokenDto.refreshToken()
        );
    }
}
