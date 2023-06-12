package com.devizones.post.rds.adapter;

import com.devizones.application.post.port.TagCommandPort;
import com.devizones.domain.post.model.Tag;
import com.devizones.post.rds.entity.TagEntity;
import com.devizones.post.rds.mapper.TagMapper;
import com.devizones.post.rds.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagCommandAdapter implements TagCommandPort {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public Tag save(String tag) {
        TagEntity tagEntity = TagEntity.builder().name(tag).build();
        return tagMapper.toDomainModel(tagRepository.save(tagEntity));
    }
}
