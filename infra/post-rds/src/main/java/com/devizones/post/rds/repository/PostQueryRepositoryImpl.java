package com.devizones.post.rds.repository;

import com.devizones.application.post.dto.PostDetailDto;
import com.devizones.application.post.dto.PostListDto;
import com.devizones.application.post.dto.PostMyPageUIDto;
import com.devizones.commons.StringUtils;
import com.devizones.post.rds.config.PostQuerydslFactory;
import com.devizones.post.rds.entity.PostEntity;
import com.devizones.post.rds.entity.PostLikeEntity;
import com.devizones.post.rds.entity.QImageEntity;
import com.devizones.post.rds.entity.QPostLikeEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.devizones.post.rds.entity.QImageEntity.imageEntity;
import static com.devizones.post.rds.entity.QPostEntity.postEntity;
import static com.devizones.post.rds.entity.QPostLikeEntity.postLikeEntity;
import static com.devizones.post.rds.entity.QPostTagEntity.postTagEntity;
import static com.devizones.post.rds.entity.QTagEntity.tagEntity;
import static com.querydsl.core.types.dsl.Wildcard.count;

@Repository
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    public PostQueryRepositoryImpl(@PostQuerydslFactory JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<PostListDto> findByPostsCursorBase(Long key, Integer size) {
        List<Long> postEntityIds = queryFactory
                .select(postEntity.id)
                .from(postEntity)
                .where(
                        isIdLessThan(key),
                        eqDeleted(false),
                        eqVisible(true)
                )
                .orderBy(postEntity.id.desc())
                .limit(size)
                .fetch();

        return getFetchPostListDto(postEntityIds);
    }

    @Override
    public Optional<PostEntity> findById(Long postId) {
        return Optional.ofNullable(queryFactory
                .select(postEntity)
                .from(postEntity)
                .where(
                        eqPostId(postId),
                        eqDeleted(false),
                        eqVisible(true)
                )
                .fetchOne());
    }

    @Override
    public Optional<PostEntity> findByIdWithOptimisticLock(Long postId) {
        return Optional.ofNullable(queryFactory
                .select(postEntity)
                .from(postEntity)
                .where(
                        eqPostId(postId),
                        eqDeleted(false),
                        eqVisible(true)
                )
                .setLockMode(LockModeType.OPTIMISTIC)
                .fetchOne());
    }

    @Override
    public Page<PostMyPageUIDto> getWrittenPost(Pageable pageable, Long writerId) {
        List<PostMyPageUIDto> content = queryFactory
                .select(Projections.constructor(PostMyPageUIDto.class,
                        postEntity.id,
                        postEntity.writerId,
                        postEntity.title,
                        imageEntity.path,
                        Expressions.as(
                                new CaseBuilder()
                                        .when(postEntity.summary.isNull())
                                        .then(postEntity.contents)
                                        .otherwise(postEntity.summary),
                                "summary"),
                        postEntity.createdAt,
                        postEntity.modifiedAt,
                        postEntity.likeCount,
                        postEntity.visible
                ))
                .from(postEntity)
                .leftJoin(postEntity.images, imageEntity)
                .on(imageEntity.thumbnail.eq(true))
                .where(
                        eqWriterId(writerId),
                        eqDeleted(false)
                        //eqVisible(true)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(postEntity.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(count)
                .from(postEntity)
                .where(
                        eqWriterId(writerId),
                        eqDeleted(false),
                        eqVisible(true)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<PostMyPageUIDto> likePost(Pageable pageable, Long writerId) {


        List<PostMyPageUIDto> content = queryFactory
                .select(Projections.constructor(PostMyPageUIDto.class,
                        postEntity.id,
                        postEntity.writerId,
                        postEntity.title,
                        imageEntity.path,
                        Expressions.as(
                                new CaseBuilder()
                                        .when(postEntity.summary.isNull())
                                        .then(postEntity.contents)
                                        .otherwise(postEntity.summary),
                                "summary"),
                        postEntity.createdAt,
                        postEntity.modifiedAt,
                        postEntity.likeCount
                ))
                .from(postEntity)
                .leftJoin(postEntity.images, imageEntity).on(imageEntity.thumbnail.eq(true))
                .where(
                        postEntity.id.eq(
                                JPAExpressions
                                        .select(postLikeEntity.postId)
                                        .from(postLikeEntity)
                                        .where(postLikeEntity.memberId.eq(writerId))
                        ),
                        eqDeleted(false),
                        eqVisible(true)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(postEntity.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(count)
                .from(postEntity)
                .where(
                        postEntity.id.eq(
                                JPAExpressions
                                        .select(postLikeEntity.postId)
                                        .from(postLikeEntity)
                                        .where(postLikeEntity.memberId.eq(writerId))
                        ),
                        eqDeleted(false),
                        eqVisible(true)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqWriterId(Long writerId) {
        return postEntity.writerId.eq(writerId);
    }

    @Override
    public Optional<PostDetailDto> findByIdDetailDto(Long postId) {

        PostDetailDto postDetailDto = queryFactory
                .select(Projections.constructor(PostDetailDto.class,
                                postEntity.id,
                                postEntity.writerId,
                                postEntity.title,
                                postEntity.contents,
                                postEntity.createdAt,
                                postEntity.likeCount
                        )
                )
                .from(postEntity)
                .where(
                        eqPostId(postId),
                        eqVisible(true),
                        eqDeleted(false)
                )
                .fetchOne();

        if (postDetailDto != null) {
            List<String> tags = queryFactory
                    .select(tagEntity.name)
                    .from(postTagEntity)
                    .leftJoin(postTagEntity.tag, tagEntity)
                    .where(
                            postTagEntity.post.id.eq(postId)
                    )
                    .fetch();
            if (!tags.isEmpty()) {
                postDetailDto.setTags(tags);
            }
        }

        return Optional.ofNullable(postDetailDto);
    }

    @Override
    public List<PostListDto> findByPostsCursorBase(Long key, Integer size, String keyword) {
        List<Long> postEntityIds = queryFactory
                .select(postEntity.id)
                .from(postEntity)
                .where(
                        isIdLessThan(key),
                        eqDeleted(false),
                        eqVisible(true),
                        containsTitle(keyword)
                )
                .orderBy(postEntity.id.desc())
                .limit(size)
                .fetch();

        return getFetchPostListDto(postEntityIds);
    }

    private List<PostListDto> getFetchPostListDto(List<Long> postEntityIds) {
        return queryFactory
                .select(
                        Projections.constructor(PostListDto.class,
                                postEntity.id,
                                postEntity.writerId,
                                postEntity.title,
                                Expressions.as(
                                        new CaseBuilder()
                                                .when(postEntity.summary.isNull())
                                                .then(postEntity.contents)
                                                .otherwise(postEntity.summary),
                                        "summary"),
                                postEntity.createdAt,
                                postEntity.likeCount,
                                imageEntity.path
                        )
                )
                .from(postEntity)
                .leftJoin(postEntity.images, imageEntity).on(imageEntity.thumbnail.eq(true))
                .where(
                        postEntity.id.in(postEntityIds)
                        //imageEntity.thumbnail.eq(true)
                )
                .orderBy(postEntity.id.desc())
                .fetch();
    }

    private BooleanExpression eqVisible(boolean visible) {
        return postEntity.visible.eq(visible);
    }

    private BooleanExpression eqPostId(Long id) {
        return postEntity.id.eq(id);
    }

    private BooleanExpression containsTitle(String keyword) {
        return (StringUtils.hasText(keyword)) ? postEntity.title.contains(keyword) : null;
    }

    private BooleanExpression isIdLessThan(Long key) {
        if (key != null && key != -1) {
            return postEntity.id.lt(key);
        }
        return null;
    }

    private BooleanExpression eqDeleted(boolean deleted) {
        return postEntity.deleted.eq(deleted);
    }
}
