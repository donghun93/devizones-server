package com.devizones.application.member.dto;

import com.devizones.domain.member.model.Member;

public record MemberDto(
        Long id,
        String email,
        String nickname,
        String profile,
        String introduce,
        String social
) {

    public static MemberDto of(Member member) {
        return new MemberDto(
                member.getId(),
                member.getEmail().getValue(),
                member.getNickname().getValue(),
                member.getProfile().getFileName(),
                member.getIntroduce().getValue(),
                member.getSocial().getDescription()
        );
    }
}
