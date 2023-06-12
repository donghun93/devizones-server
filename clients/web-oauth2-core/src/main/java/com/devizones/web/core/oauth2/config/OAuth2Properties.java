package com.devizones.web.core.oauth2.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@ConfigurationProperties(prefix = "oauth2")
public class OAuth2Properties {
    private final List<String> authorizedRedirectUris = new ArrayList<>();
}