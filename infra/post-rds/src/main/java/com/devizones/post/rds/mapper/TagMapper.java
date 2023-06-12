package com.devizones.post.rds.mapper;

import com.devizones.domain.post.model.Tag;
import com.devizones.post.rds.entity.TagEntity;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public Tag toDomainModel(TagEntity tagEntity) {
        return Tag.builder()
                  .id(tagEntity.getId())
                  .tagName(tagEntity.getName())
                  .build();
    }

    public TagEntity toEntity(Tag tag) {
        return TagEntity.builder()
                        .id(tag.getId())
                        .name(tag.getValue())
                        .build();
    }
}
