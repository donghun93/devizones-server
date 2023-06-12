package com.devizones.post.rds.adapter;

import com.devizones.application.post.port.TagQueryPort;
import com.devizones.domain.post.model.Tag;
import com.devizones.post.rds.entity.TagEntity;
import com.devizones.post.rds.exception.PostJpaErrorCode;
import com.devizones.post.rds.exception.PostJpaException;
import com.devizones.post.rds.mapper.TagMapper;
import com.devizones.post.rds.repository.TagQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TagQueryAdapter implements TagQueryPort {
    private final TagQueryRepository tagQueryRepository;
    private final TagMapper tagMapper;

    @Override
    public boolean isTagExist(String tag) {
        return tagQueryRepository.isTagExist(tag);
    }

    @Override
    public Tag findByName(String name) {
        TagEntity tagEntity = tagQueryRepository.findByName(name)
                                                .orElseThrow(() -> new PostJpaException(PostJpaErrorCode.TAG_NOT_FOUND));
        return tagMapper.toDomainModel(tagEntity);
    }

    @Override
    public Optional<Tag> findByNameOptional(String tag) {
        Optional<TagEntity> tagEntity = tagQueryRepository.findByName(tag);
        return tagEntity.map(tagMapper::toDomainModel);
    }
}
