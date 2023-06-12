package com.devizones.post.rds.mapper;

import com.devizones.domain.like.model.PostLike;
import com.devizones.post.rds.entity.PostLikeEntity;
import org.springframework.stereotype.Component;

@Component
public class PostLikeMapper {

    public PostLikeEntity toEntity(PostLike postLike) {
        return PostLikeEntity.builder()
                             .postId(postLike.getPostId())
                             .memberId(postLike.getMemberId())
                             .build();
    }
}
