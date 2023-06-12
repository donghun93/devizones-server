package com.devizones.application.member.port;

import com.devizones.domain.member.model.Member;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MemberLoadPort {
    Member findById(Long memberId);
    Member findByEmail(String email);
    boolean existByEmail(String email);
    boolean existByNickname(String nickname);
    Map<Long, Member> findByIdIn(Set<Long> memberIds);
}
