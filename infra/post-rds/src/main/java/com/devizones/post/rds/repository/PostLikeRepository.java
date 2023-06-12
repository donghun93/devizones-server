package com.devizones.post.rds.repository;

import com.devizones.post.rds.entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    void deleteByPostIdAndMemberId(Long postId, Long memberId);
}
