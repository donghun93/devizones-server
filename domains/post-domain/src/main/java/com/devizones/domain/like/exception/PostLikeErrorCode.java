package com.devizones.domain.like.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostLikeErrorCode {

    POST_ID_VALID("게시글 id값이 없습니다."),
    MEMBER_ID_VALID("회원 id값이 없습니다."),
    ;

    private final String message;
}
