package com.devizones.image.s3.adapter.exception;

import com.devizones.commons.ErrorCode;
import lombok.Getter;

@Getter
public class AwsS3Exception extends RuntimeException {
    private final ErrorCode errorCode;

    public AwsS3Exception(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
