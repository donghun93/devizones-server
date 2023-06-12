package com.devizones.redis.refreshtoken;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor
@RedisHash(value = "refresh")
public class RefreshToken {
    @Id
    private String id;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private Long expirationInSeconds;

    @Builder
    private RefreshToken(String id, String refreshToken, Long expirationInSeconds) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.expirationInSeconds = expirationInSeconds;
    }
}
