package com.devizones.post.rds.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_like")
public class PostLikeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long postId;
    @Column(nullable = false, updatable = false)
    private Long memberId;

    @Builder
    private PostLikeEntity(Long id, Long postId, Long memberId) {
        this.id = id;
        this.postId = postId;
        this.memberId = memberId;
    }
}
