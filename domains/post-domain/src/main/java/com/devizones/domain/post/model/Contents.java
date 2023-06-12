package com.devizones.domain.post.model;

import com.devizones.commons.StringUtils;
import com.devizones.domain.post.exception.PostErrorCode;
import com.devizones.domain.post.exception.PostException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Contents {
    private String value;

    @Builder
    private Contents(String contents) {
        setContents(contents);
    }

    private void setContents(String contents) {
        if(!StringUtils.hasText(contents)) {
            throw new PostException(PostErrorCode.CONTENTS_EMPTY);
        }

        this.value = contents;
    }

    public static Contents create(String contents) {
        return new Contents(contents);
    }

}
