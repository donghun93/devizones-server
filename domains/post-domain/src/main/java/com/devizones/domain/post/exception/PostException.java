package com.devizones.domain.post.exception;

import com.devizones.commons.ErrorCode;
import lombok.Getter;

@Getter
public class PostException extends RuntimeException {
    private final ErrorCode errorCode;

    public PostException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public PostException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
