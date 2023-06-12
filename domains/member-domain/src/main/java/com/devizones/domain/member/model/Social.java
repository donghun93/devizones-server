package com.devizones.domain.member.model;

import com.devizones.domain.member.exception.MemberErrorCode;
import com.devizones.domain.member.exception.MemberException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Social {
    NAVER("naver"),
    KAKAO("kakao"),
    GOOGLE("google")
    ;

    private final String description;


    public static Social convert(String social) {
        try {
            return Social.valueOf(social.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MemberException(MemberErrorCode.SOCIAL_NOT_SUPPORT);
        }
    }
}
