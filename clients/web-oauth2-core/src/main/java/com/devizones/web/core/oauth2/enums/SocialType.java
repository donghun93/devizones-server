package com.devizones.web.core.oauth2.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao"),
    VILLAGER("villager")
    ;

    private final String socialName;
}