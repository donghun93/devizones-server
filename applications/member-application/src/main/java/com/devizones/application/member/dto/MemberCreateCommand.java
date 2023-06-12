package com.devizones.application.member.dto;

import com.devizones.domain.member.dto.CreateMemberDomainModelDto;

public record MemberCreateCommand(
        String email,
        String nickname,
        String social
) {

    public CreateMemberDomainModelDto toDomainModelDto() {
        return new CreateMemberDomainModelDto(email, nickname, social);
    }
}
