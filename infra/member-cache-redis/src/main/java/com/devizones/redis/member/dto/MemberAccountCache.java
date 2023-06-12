package com.devizones.redis.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberAccountCache {
    private Long id;
    private String email;

    private MemberAccountCache(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static MemberAccountCache of(Long id, String email) {
        return new MemberAccountCache(id, email);
    }
}
