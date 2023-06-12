package com.devizones.application.post.port;

import com.devizones.domain.post.model.Tag;

import java.util.Optional;

public interface TagQueryPort {
    boolean isTagExist(String tag);

    Tag findByName(String tag);
    Optional<Tag> findByNameOptional(String tag);
}
