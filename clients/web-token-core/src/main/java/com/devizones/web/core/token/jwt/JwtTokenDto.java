package com.devizones.web.core.token.jwt;

import lombok.Builder;

public record JwtTokenDto(String grantType,
                          String accessToken,
                          String refreshToken,
                          Long refreshTokenExpirationTime) {
    @Builder
    public JwtTokenDto {
    }
}