package com.devizones.member.rds.adapter;

import com.devizones.application.member.port.MemberLoadPort;
import com.devizones.domain.member.model.Member;
import com.devizones.member.rds.entity.MemberEntity;
import com.devizones.member.rds.exception.MemberJpaErrorCode;
import com.devizones.member.rds.exception.MemberJpaException;
import com.devizones.member.rds.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MemberQueryAdapter implements MemberLoadPort {
    private final MemberQueryRepository memberQueryRepository;
    private final MemberMapper memberMapper;

    @Override
    public Member findById(Long memberId) {
        MemberEntity memberEntity = memberQueryRepository.findById(memberId)
                                                         .orElseThrow(() -> new MemberJpaException(MemberJpaErrorCode.MEMBER_NOT_FOUND));
        return memberMapper.toDomain(memberEntity);
    }

    @Override
    public Member findByEmail(String email) {
        MemberEntity memberEntity = memberQueryRepository.findByEmail(email)
                                                         .orElseThrow(() -> new MemberJpaException(MemberJpaErrorCode.MEMBER_NOT_FOUND));
        return memberMapper.toDomain(memberEntity);
    }

    @Override
    public boolean existByEmail(String email) {
        return memberQueryRepository.existByEmail(email);
    }

    @Override
    public boolean existByNickname(String nickname) {
        return memberQueryRepository.existByNickname(nickname);
    }

    @Override
    public Map<Long, Member> findByIdIn(Set<Long> memberIds) {
        List<MemberEntity> memberEntities = memberQueryRepository.findByIdIn(memberIds);
        return memberEntities.stream()
                .collect(Collectors.toMap(MemberEntity::getId, memberMapper::toDomain));
    }

}
