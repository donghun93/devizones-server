package com.devizones.application.post.dto;

import com.devizones.domain.post.dto.CreatePostDomainModelDto;
import com.devizones.domain.post.model.Tag;

import java.io.InputStream;
import java.util.List;

public record PostCreateCommand(
        String title,
        String contents,
        List<String> tags,
        boolean visible,
        String thumbnailFilename,
        InputStream thumbnail,
        String summary
        // List<String> images, 이건 보류 batch로 처리해도 될듯
) {
    public CreatePostDomainModelDto toDomainModelDto(Long writerId, List<Tag> tags) {
        return new CreatePostDomainModelDto(
            writerId,
                title,
                contents,
                tags,
                (thumbnail == null) ? null : thumbnailFilename,
                summary,
                visible
        );
    }
}
