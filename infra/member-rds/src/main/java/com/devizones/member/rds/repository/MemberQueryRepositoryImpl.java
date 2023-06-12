package com.devizones.member.rds.repository;

import com.devizones.member.rds.config.MemberQuerydslFactory;
import com.devizones.member.rds.entity.MemberEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.devizones.member.rds.entity.QMemberEntity.memberEntity;
import static java.util.Optional.ofNullable;

@Repository
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepositoryImpl(@MemberQuerydslFactory JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<MemberEntity> findById(Long memberId) {
        return ofNullable(
                queryFactory
                        .select(memberEntity)
                        .from(memberEntity)
                        .where(
                                idEq(memberId),
                                isActive()
                        )
                        .fetchOne()
        );
    }

    @Override
    public boolean existByEmail(String email) {
        return queryFactory
                .select(memberEntity)
                .from(memberEntity)
                .where(
                        eqEmail(email),
                        isActive()
                )
                .fetchFirst() != null;
    }

    @Override
    public boolean existByNickname(String nickname) {
        return queryFactory
                .select(memberEntity)
                .from(memberEntity)
                .where(
                        eqNickname(nickname)
                )
                .fetchFirst() != null;
    }

    private static BooleanExpression eqNickname(String nickname) {
        return memberEntity.nickname.eq(nickname);
    }

    @Override
    public Optional<MemberEntity> findByEmail(String email) {
        return ofNullable(
                queryFactory
                        .select(memberEntity)
                        .from(memberEntity)
                        .where(
                                eqEmail(email),
                                isActive()
                        )
                        .fetchOne()
        );
    }

    @Override
    public List<MemberEntity> findByIdIn(Set<Long> memberIds) {
        return queryFactory
                .select(memberEntity)
                .from(memberEntity)
                .where(
                        idIn(memberIds),
                        isActive()
                )
                .fetch();
    }

    private BooleanExpression idEq(Long memberId) {
        return memberEntity.id.eq(memberId);
    }

    private BooleanExpression idIn(Set<Long> memberIds) {
        if(memberIds.isEmpty()) {
            return null;
        }
        return memberEntity.id.in(memberIds);
    }

    private BooleanExpression eqEmail(String email) {
        return StringUtils.hasText(email) ? memberEntity.email.eq(email) : null;
    }

    private BooleanExpression isActive() {
        return memberEntity.deleted.eq(false);
    }
}
