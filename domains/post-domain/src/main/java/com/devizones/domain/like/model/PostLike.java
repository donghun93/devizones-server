package com.devizones.domain.like.model;

import com.devizones.domain.like.exception.PostLikeException;
import lombok.Getter;

import java.util.Objects;

import static com.devizones.domain.like.exception.PostLikeErrorCode.MEMBER_ID_VALID;
import static com.devizones.domain.like.exception.PostLikeErrorCode.POST_ID_VALID;

@Getter
public class PostLike {
    private final Long postId;
    private final Long memberId;

    private PostLike(Long postId, Long memberId) {

        if (Objects.isNull(postId)) {
            throw new PostLikeException(POST_ID_VALID);
        }

        if (Objects.isNull(memberId)) {
            throw new PostLikeException(MEMBER_ID_VALID);
        }

        this.postId = postId;
        this.memberId = memberId;
    }

    public static PostLike create(Long postId, Long memberId) {
        return new PostLike(postId, memberId);
    }
}
