package com.devizones.post.rds.adapter;

import com.devizones.application.post.port.PostLikeCommandPort;
import com.devizones.domain.like.model.PostLike;
import com.devizones.post.rds.entity.PostLikeEntity;
import com.devizones.post.rds.mapper.PostLikeMapper;
import com.devizones.post.rds.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostLikeCommandAdapter implements PostLikeCommandPort {
    private final PostLikeRepository postLikeRepository;
    private final PostLikeMapper postLikeMapper;

    @Override
    public void save(PostLike postLike) {
        PostLikeEntity postLikeEntity = postLikeMapper.toEntity(postLike);
        postLikeRepository.save(postLikeEntity);
    }

    @Override
    public void delete(PostLike postLike) {
        postLikeRepository.deleteByPostIdAndMemberId(postLike.getPostId(), postLike.getMemberId());
    }
}
