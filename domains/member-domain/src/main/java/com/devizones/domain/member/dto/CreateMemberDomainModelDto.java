package com.devizones.domain.member.dto;

public record CreateMemberDomainModelDto(
        String email,
        String nickname,
        String social
) {
}
