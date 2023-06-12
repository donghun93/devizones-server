package com.devizones.domain.post.dto;

import com.devizones.domain.post.model.Tag;

import java.util.List;

public record CreatePostDomainModelDto(
        Long writerId,
        String title,
        String contents,
        List<Tag> tags,
        String thumbnail,
        String summary,
        boolean visible
) {
}
