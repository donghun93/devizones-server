package com.devizones.redis.member.repository;

import com.devizones.redis.member.entity.MemberCacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberCacheJpaRepository extends JpaRepository<MemberCacheEntity, Long> {

    @Query("select m from MemberCacheEntity m where m.email = :email and m.deleted = false")
    Optional<MemberCacheEntity> findByEmail(String email);
}
