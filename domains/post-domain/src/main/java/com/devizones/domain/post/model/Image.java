package com.devizones.domain.post.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Image {
    private final Long id;
    private final String path;
    private final ImageFormat imageFormat;
    private boolean thumbnail;

    @Builder
    private Image(Long id, String path, boolean thumbnail) {
        this.id = id;
        this.imageFormat = ImageFormat.validateAndExtractFormat(path);
        this.path = path;
        this.thumbnail = thumbnail;
    }

    public static Image create(String path, boolean isThumbnail) {
        return Image.builder()
                    .id(null)
                    .path(path)
                    .thumbnail(isThumbnail)
                    .build();
    }

    public void setThumbnail(boolean thumbnail) {
        this.thumbnail = thumbnail;
    }
}
