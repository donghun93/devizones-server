package com.devizones.redis.member.service;

import com.devizones.redis.member.CacheKey;
import com.devizones.redis.member.dto.MemberAccountCache;
import com.devizones.redis.member.entity.MemberCacheEntity;
import com.devizones.redis.member.exception.MemberRedisException;
import com.devizones.redis.member.repository.MemberCacheJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devizones.redis.member.exception.MemberRedisErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberAccountCacheServiceImpl implements MemberAccountCacheService {
    private final MemberCacheJpaRepository memberCacheJpaRepository;

    @Override
    @Cacheable(value = CacheKey.MEMBER, key = "#email", unless = "#result == null")
    public MemberAccountCache findByEmail(String email) {
        MemberCacheEntity memberCacheEntity = memberCacheJpaRepository.findByEmail(email)
                                                                      .orElseThrow(() -> new MemberRedisException(MEMBER_NOT_FOUND));

        return MemberAccountCache.of(memberCacheEntity.getId(), memberCacheEntity.getEmail());
    }

    @CacheEvict(value = CacheKey.MEMBER, allEntries = true)
    @Scheduled(fixedRateString = "${caching.member.ttl}")
    public void clearMemberCache() {
    }
}
