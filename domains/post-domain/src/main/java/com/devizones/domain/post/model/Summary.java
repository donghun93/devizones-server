package com.devizones.domain.post.model;

import com.devizones.commons.StringUtils;
import com.devizones.domain.post.exception.PostErrorCode;
import com.devizones.domain.post.exception.PostException;
import lombok.Getter;

import static com.devizones.commons.StringUtils.hasText;

@Getter
public class Summary {
    public static final int MAX_SUMMARY_LENGTH = 50;
    private String value;

    private Summary(String summary) {
        setSummary(summary);
    }

    public boolean isEmpty() {
        return !hasText(value);
    }
    private void setSummary(String summary) {
        if(hasText(summary) && summary.length() > MAX_SUMMARY_LENGTH) {
            throw new PostException(PostErrorCode.SUMMARY_MAX_LENGTH_OVER);
        }

        this.value = hasText(summary) ? summary : null;
    }

    public static Summary create(String summary) {
        return new Summary(summary);
    }
}
