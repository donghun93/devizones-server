package com.devizones.application.post.dto;

import com.devizones.domain.post.dto.CreatePostDomainModelDto;
import com.devizones.domain.post.dto.EditPostDomainModelDto;
import com.devizones.domain.post.model.Tag;

import java.io.InputStream;
import java.util.List;

public record PostEditCommand(
        String title,
        String contents,
        List<String> tags,
        boolean visible,
        String summary,
        String thumbnailFilename,
        InputStream thumbnail
) {
    public EditPostDomainModelDto toDomainModelDto(List<Tag> tags) {
        return new EditPostDomainModelDto(
                title,
                contents,
                tags,
                visible,
                (thumbnail == null) ? null : thumbnailFilename,
                summary
        );
    }
}
