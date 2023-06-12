package com.devizones.application.post.exception;

import com.devizones.commons.ErrorCode;
import com.devizones.domain.member.exception.MemberException;
import com.devizones.domain.post.exception.PostException;

public class PostServiceException extends PostException {
    public PostServiceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
