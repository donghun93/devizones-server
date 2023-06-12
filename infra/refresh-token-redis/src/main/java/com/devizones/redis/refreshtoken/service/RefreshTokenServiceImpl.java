package com.devizones.redis.refreshtoken.service;

import com.devizones.redis.refreshtoken.RefreshToken;
import com.devizones.redis.refreshtoken.repository.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Override
    public void save(String key, String refreshToken, Long expirationTime) {
        RefreshToken token = RefreshToken.builder()
                                                .id(key)
                                                .refreshToken(refreshToken)
                                                .expirationInSeconds(expirationTime)
                                                .build();
        refreshTokenRedisRepository.save(token);
    }
}
