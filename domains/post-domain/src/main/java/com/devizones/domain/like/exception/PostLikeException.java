package com.devizones.domain.like.exception;

import lombok.Getter;

@Getter
public class PostLikeException extends RuntimeException {
    private PostLikeErrorCode errorCode;

    public PostLikeException(PostLikeErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public PostLikeException(String message, PostLikeErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
