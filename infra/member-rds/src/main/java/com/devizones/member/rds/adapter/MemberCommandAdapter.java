package com.devizones.member.rds.adapter;

import com.devizones.application.member.port.MemberCommandPort;
import com.devizones.domain.member.model.Member;
import com.devizones.member.rds.entity.MemberEntity;
import com.devizones.member.rds.exception.MemberJpaErrorCode;
import com.devizones.member.rds.exception.MemberJpaException;
import com.devizones.member.rds.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberCommandAdapter implements MemberCommandPort {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public Member save(Member member) {
        MemberEntity entity = memberMapper.toEntity(member);
        return memberMapper.toDomain(memberRepository.save(entity));
    }

    @Override
    public void update(Member member) {
        MemberEntity memberEntity = memberRepository.findById(member.getId())
                                                    .orElseThrow(() -> new MemberJpaException(MemberJpaErrorCode.MEMBER_NOT_FOUND));
        memberEntity.update(member);
    }
}
