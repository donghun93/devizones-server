package com.devizones.post.rds.repository;


import com.devizones.post.rds.entity.TagEntity;

import java.util.Optional;

public interface TagQueryRepository {
    boolean isTagExist(String tag);
    Optional<TagEntity> findByName(String name);
}
