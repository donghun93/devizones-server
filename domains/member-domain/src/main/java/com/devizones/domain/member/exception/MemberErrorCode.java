package com.devizones.domain.member.exception;

import com.devizones.commons.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    EMAIL_EMPTY("이메일이 없습니다."),
    EMAIL_FORMAT_ERROR("이메일 형식이 아닙니다."),

    NICKNAME_EMPTY("닉네임이 없습니다."),
    NICKNAME_FORMAT_ERROR("닉네임 유효성 검사에 실패하였습니다."),

    PROFILE_EMPTY("파일명이 존재하지 않습니다."),
    PROFILE_NOT_SUPPORT("지원하지 않는 포맷입니다."),

    SOCIAL_NOT_SUPPORT("지원하지 않는 소셜 로그인입니다."),

    INTRODUCE_EMPTY("자기소개가 없습니다."),
    INTRODUCE_MAX_LENGTH_OVER("자기소개 최대수를 초과하였습니다."),
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
