package com.devizones.application.member.port;

import com.devizones.domain.member.model.Member;

public interface MemberCommandPort {
    Member save(Member member);
    void update(Member member);
}
