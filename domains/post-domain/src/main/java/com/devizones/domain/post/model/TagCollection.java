package com.devizones.domain.post.model;

import com.devizones.domain.post.exception.PostErrorCode;
import com.devizones.domain.post.exception.PostException;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class TagCollection {
    public static final int MAX_SIZE = 5;
    private List<Tag> tags = new ArrayList<>();

    @Builder
    private TagCollection(List<Tag> tags) {
        if (!Objects.isNull(tags)) {
            for (Tag tag : tags) {
                addTag(tag);
            }
        }
    }

    public boolean isEmpty() {
        return tags.isEmpty();
    }

    private void addTag(Tag tag) {
        if (this.tags.size() >= MAX_SIZE) {
            throw new PostException(PostErrorCode.TAG_MAX_SIZE_OVER);
        }

        if (tags.stream()
                .anyMatch(existingTag -> existingTag.equals(tag))) {
            throw new PostException(PostErrorCode.TAG_DUPLICATE);
        }

        this.tags.add(tag);
    }

    public static TagCollection create(List<Tag> tags) {
        return new TagCollection(tags);
    }
}
