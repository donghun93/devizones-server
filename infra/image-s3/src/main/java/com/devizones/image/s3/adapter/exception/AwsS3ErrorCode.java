package com.devizones.image.s3.adapter.exception;

import com.devizones.commons.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AwsS3ErrorCode implements ErrorCode {
    UPLOAD_FAIL("업로드에 실패하였습니다.")
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
