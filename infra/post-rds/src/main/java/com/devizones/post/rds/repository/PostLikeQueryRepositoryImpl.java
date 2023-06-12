package com.devizones.post.rds.repository;

import com.devizones.post.rds.config.PostQuerydslFactory;
import com.devizones.post.rds.entity.QPostLikeEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import static com.devizones.post.rds.entity.QPostLikeEntity.postLikeEntity;

@Repository
public class PostLikeQueryRepositoryImpl implements PostLikeQueryRepository {
    private final JPAQueryFactory queryFactory;

    public PostLikeQueryRepositoryImpl(@PostQuerydslFactory JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public boolean existByPostIdAndMemberId(Long postId, Long memberId) {
        return queryFactory
                .select(postLikeEntity)
                .from(postLikeEntity)
                .where(
                        postLikeEntity.postId.eq(postId),
                        postLikeEntity.memberId.eq(memberId)
                )
                .fetchOne() != null;
    }
}
