package com.devizones.web.core.token.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@Setter
@ConfigurationProperties(prefix = "token")
public class JwtTokenProperties {

    private String tokenSecret;
    private long accessTokenExpirationSeconds;
    private long refreshTokenExpirationSeconds;

}