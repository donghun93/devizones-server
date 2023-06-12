package com.devizones.application.member.exception;

import com.devizones.commons.ErrorCode;
import com.devizones.domain.member.exception.MemberException;

public class MemberServiceException extends MemberException {
    public MemberServiceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
