package com.devizones.redis.refreshtoken.service;

public interface RefreshTokenService {
    void save(String key, String refreshToken, Long expirationTime);
}
