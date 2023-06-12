package com.devizones.application.post.port;

import com.devizones.domain.post.model.Tag;

public interface TagCommandPort {
    Tag save(String tag);
}
