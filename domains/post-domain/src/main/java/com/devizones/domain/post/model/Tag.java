package com.devizones.domain.post.model;

import com.devizones.commons.StringUtils;
import com.devizones.domain.post.exception.PostException;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

import static com.devizones.domain.post.exception.PostErrorCode.TAG_NOT_VALID;

@Getter
public class Tag {
    private Long id;
    private String value;

    @Builder
    private Tag(Long id, String tagName) {
        setTag(id, tagName);
    }

    private void setTag(Long id, String tagName) {
        if (Objects.isNull(id) || !StringUtils.hasText(tagName)) {
            throw new PostException(TAG_NOT_VALID);
        }

        this.id = id;
        this.value = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(getId(), tag.getId()) || Objects.equals(getValue(), tag.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue());
    }
}
