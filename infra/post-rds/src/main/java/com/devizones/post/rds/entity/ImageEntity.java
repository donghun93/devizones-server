package com.devizones.post.rds.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_image")
public class ImageEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    private boolean thumbnail;

    @Builder
    private ImageEntity(Long id, String path, boolean thumbnail) {
        this.id = id;
        this.path = path;
        this.thumbnail = thumbnail;
    }
}
