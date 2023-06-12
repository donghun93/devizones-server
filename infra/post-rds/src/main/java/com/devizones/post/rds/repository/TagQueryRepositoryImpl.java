package com.devizones.post.rds.repository;

import com.devizones.post.rds.config.PostQuerydslFactory;
import com.devizones.post.rds.entity.TagEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.devizones.post.rds.entity.QTagEntity.tagEntity;

@Repository
public class TagQueryRepositoryImpl implements TagQueryRepository {

    private final JPAQueryFactory queryFactory;

    public TagQueryRepositoryImpl(@PostQuerydslFactory JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public boolean isTagExist(String name) {
        return queryFactory
                .select(tagEntity)
                .from(tagEntity)
                .where(tagEntity.name.eq(name))
                .fetchOne() != null;
    }

    @Override
    public Optional<TagEntity> findByName(String name) {
        return Optional.ofNullable(
                queryFactory
                        .select(tagEntity)
                        .from(tagEntity)
                        .where(tagEntity.name.eq(name))
                        .fetchOne()
        );
    }
}
