package com.devizones.post.rds.adapter;

import com.devizones.application.post.port.PostLikeQueryPort;
import com.devizones.post.rds.repository.PostLikeQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostLikeQueryAdapter implements PostLikeQueryPort {

    private final PostLikeQueryRepository postLikeQueryRepository;

    @Override
    public boolean existByPostIdAndMemberId(Long postId, Long memberId) {
        return postLikeQueryRepository.existByPostIdAndMemberId(postId, memberId);
    }
}
