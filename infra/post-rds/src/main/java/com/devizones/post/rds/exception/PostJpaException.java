package com.devizones.post.rds.exception;

import com.devizones.commons.ErrorCode;
import com.devizones.domain.post.exception.PostException;

public class PostJpaException extends PostException {
    public PostJpaException(ErrorCode errorCode) {
        super(errorCode);
    }
}
