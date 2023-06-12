package com.devizones.post.rds.repository;

import com.devizones.post.rds.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
