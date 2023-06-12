package com.devizones.post.rds.repository;

import com.devizones.post.rds.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
