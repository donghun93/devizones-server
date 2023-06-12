package com.devizones.domain.post.model;

import com.devizones.commons.StringUtils;
import com.devizones.domain.post.exception.PostErrorCode;
import com.devizones.domain.post.exception.PostException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Title {
    public final static int MIN_LENGTH = 1;
    public final static int MAX_LENGTH = 20;
    private String value;

    @Builder
    private Title(String title) {
        setTitle(title);
    }

    private void setTitle(String title) {
        if(!StringUtils.hasText(title)) {
            throw new PostException(PostErrorCode.TITLE_EMPTY);
        }

        if (title.length() < MIN_LENGTH || title.length() > MAX_LENGTH) {
            throw new PostException(
                    String.format("제목은 %d에서 %d까지 입력해주세요", MIN_LENGTH, MAX_LENGTH),
                    PostErrorCode.TITLE_NOT_VALID
            );
        }

        this.value = title;
    }

    public static Title create(String title) {
        return new Title(title);
    }
}
