package com.devizones.application.post.port;

public interface PostLikeQueryPort {
    boolean existByPostIdAndMemberId(Long postId, Long memberId);
}
