package com.devizones.domain.post.exception;

import com.devizones.commons.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {
    TITLE_EMPTY("게시글 제목이 없습니다."),
    TITLE_NOT_VALID("게시글 제목이 유효성 검사에 실패하였습니다."),
    WRITER_EMPTY("게시글 작성자가 없습니다."),

    CONTENTS_EMPTY("게시글 본문이 없습니다"),

    IMAGE_EMPTY("이미지 파일명이 존재하지 않습니다."),

    IMAGE_NOT_SUPPORT("지원하지 않는 포맷입니다."),

    TAG_NOT_VALID("잘못된 태그입니다."),
    TAG_MAX_SIZE_OVER("태그 입력 가능한 초과하였습니다."),
    TAG_DUPLICATE("태그가 중복되었습니다."),

    SUMMARY_MAX_LENGTH_OVER("요약 작성 길이가 초과하였습니다."),

    POST_ALREADY_DELETED("게시글이 이미 삭제되었습니다."),
    POST_NOT_OWNER("게시글 작성자가 아닙니다.")
    ;

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
