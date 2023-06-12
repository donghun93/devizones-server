package com.devizones.member.rds.exception;

import com.devizones.commons.ErrorCode;
import com.devizones.domain.member.exception.MemberException;

public class MemberJpaException extends MemberException {
    public MemberJpaException(ErrorCode errorCode) {
        super(errorCode);
    }
}
