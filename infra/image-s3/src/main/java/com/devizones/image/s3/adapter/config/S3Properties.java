package com.devizones.image.s3.adapter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cloud.aws.s3")
@Data
public class S3Properties {

    private String bucket;
    private String profilePrefix;
    private String postPrefix;
    private String baseUrl;

    public String getProfileAbsolutePath() {
        return baseUrl + profilePrefix;
    }
    public String getPostAbsolutePath() {
        return baseUrl + postPrefix;
    }
}
