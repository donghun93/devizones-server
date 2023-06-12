package com.devizones.application.post.service;

import com.devizones.application.post.dto.PostDetailDto;
import com.devizones.application.post.port.PostCommandPort;
import com.devizones.application.post.port.PostLikeCommandPort;
import com.devizones.domain.like.model.PostLike;
import com.devizones.domain.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeFacade {


    private final PostCommandPort postCommandPort;
    private final PostLikeCommandPort postLikeCommandPort;

    @Transactional
    public void like(Long postId, Long memberId) {
        postCommandPort.like(postId);
        postLikeCommandPort.save(PostLike.create(postId, memberId));
    }

    @Transactional
    public void unlike(Long postId, Long memberId) {
        postCommandPort.unlike(postId);
        postLikeCommandPort.delete(PostLike.create(postId, memberId));
    }
}
