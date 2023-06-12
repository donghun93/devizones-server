package com.devizones.application.member.service;

import com.devizones.application.member.dto.MemberDto;
import com.devizones.application.member.port.MemberLoadPort;
import com.devizones.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberLoadPort memberLoadPort;

    public MemberDto getMember(Long memberId) {
        Member member = memberLoadPort.findById(memberId);
        return MemberDto.of(member);
    }
}
