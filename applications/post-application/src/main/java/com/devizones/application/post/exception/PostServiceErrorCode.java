package com.devizones.application.post.exception;

import com.devizones.commons.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostServiceErrorCode implements ErrorCode {
    POST_OPTIMISTIC_LOCK_LIKE_FAIL("좋아요 실패하였습니다."),
    POST_LIKE_ALREADY("좋아요가 이미 눌러졌습니다."),
    POST_LIKE_NOT_EXIST("좋아요가 없습니다."),
    POST_WRITER_NOT_MATCH("게시글 작성자가 아닙니다.")
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
