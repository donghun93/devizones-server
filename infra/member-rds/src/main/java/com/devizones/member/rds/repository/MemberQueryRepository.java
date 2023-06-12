package com.devizones.member.rds.repository;

import com.devizones.member.rds.entity.MemberEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MemberQueryRepository {
    Optional<MemberEntity> findById(Long memberId);
    boolean existByEmail(String email);
    boolean existByNickname(String nickname);
    Optional<MemberEntity> findByEmail(String email);
    List<MemberEntity> findByIdIn(Set<Long> memberIds);
}
