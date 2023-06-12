package com.devizones.application.post.port;

import com.devizones.domain.post.model.Post;

public interface PostCommandPort {
    Post save(Post post);
    void update(Post post);
    void delete(Post post);
    void like(Long postId);
    void unlike(Long postId);
}
