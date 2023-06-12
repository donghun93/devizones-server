package com.devizones.post.rds.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostQuerydslConfig {
    @PersistenceContext
    private EntityManager em;

    @Bean
    @PostQuerydslFactory
    public JPAQueryFactory postJpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}