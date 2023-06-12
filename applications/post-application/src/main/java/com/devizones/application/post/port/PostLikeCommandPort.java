package com.devizones.application.post.port;

import com.devizones.domain.like.model.PostLike;

public interface PostLikeCommandPort {
    void save(PostLike postLike);
    void delete(PostLike postLike);

}
