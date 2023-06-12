package com.devizones.domain.post.dto;

import com.devizones.domain.post.model.Tag;

import java.util.List;

public record EditPostDomainModelDto(
        String title,
        String contents,
        List<Tag> tags,
        boolean visible,
        String thumbnailFilename,
        String summary
) {
}
