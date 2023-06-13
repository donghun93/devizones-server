package com.devizones.web.core.security.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    private final List<String> origins = new ArrayList<>();
    private final List<String> methods = new ArrayList<>();
    private final List<String> allowedHeaders = new ArrayList<>();
    private final List<String> exposedHeaders = new ArrayList<>();
}
