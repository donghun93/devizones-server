package com.devizones.post.rds.exception;

import com.devizones.commons.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostJpaErrorCode implements ErrorCode {
    TAG_NOT_FOUND("태그가 존재하지 않습니다."),
    POST_NOT_FOUND("게시글이 존재하지 않습니다."),
    ;


    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
