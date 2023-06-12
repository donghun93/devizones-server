package com.devizones.application.post.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "concurrency")
public class ConcurrencyProperties {

    private final Like like = new Like();

    public static class Like {
        private Integer maxRetry;
        private Integer waitTime;

        public Integer getMaxRetry() {
            return maxRetry;
        }

        public void setMaxRetry(Integer maxRetry) {
            this.maxRetry = maxRetry;
        }

        public Integer getWaitTime() {
            return waitTime;
        }

        public void setWaitTime(Integer waitTime) {
            this.waitTime = waitTime;
        }
    }

    public Integer getMaxRetry() {
        return like.maxRetry;
    }

    public Integer getWaitTime() {
        return like.waitTime;
    }

    public Like getLike() {
        return like;
    }
}
