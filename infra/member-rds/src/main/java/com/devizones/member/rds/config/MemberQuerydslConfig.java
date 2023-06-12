package com.devizones.member.rds.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberQuerydslConfig {
    @PersistenceContext
    private EntityManager em;

    @Bean
    @MemberQuerydslFactory
    public JPAQueryFactory memberJpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}