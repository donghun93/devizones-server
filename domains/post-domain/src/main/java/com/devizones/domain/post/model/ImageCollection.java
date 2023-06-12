package com.devizones.domain.post.model;


import com.devizones.domain.post.service.ContentsImageAnalyzeDomainService;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.devizones.commons.StringUtils.hasText;

@Getter
public class ImageCollection {
    private final List<Image> images = new ArrayList<>();

    @Builder
    private ImageCollection(List<Image> images) {
        this.images.addAll(images);
    }

    public static ImageCollection create(List<Image> images) {
        return ImageCollection.builder()
                              .images(images)
                              .build();
    }
}
