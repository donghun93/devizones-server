package com.devizones.post.rds.repository;

public interface PostLikeQueryRepository {
    boolean existByPostIdAndMemberId(Long postId, Long memberId);
}
