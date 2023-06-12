package com.devizones.redis.member.service;


import com.devizones.redis.member.dto.MemberAccountCache;

public interface MemberAccountCacheService {
    MemberAccountCache findByEmail(String email);
}
