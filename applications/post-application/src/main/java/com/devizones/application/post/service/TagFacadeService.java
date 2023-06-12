package com.devizones.application.post.service;

import com.devizones.application.post.port.TagCommandPort;
import com.devizones.application.post.port.TagQueryPort;
import com.devizones.domain.post.model.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagFacadeService {

    private final TagQueryPort tagQueryPort;
    private final TagCommandPort tagCommandPort;

    @Transactional
    public List<Tag> createOrFind(List<String> tags) {
        List<Tag> tagList = new ArrayList<>();
        for (String tag : tags) {
            if(StringUtils.hasText(tag)) {
                Optional<Tag> result = tagQueryPort.findByNameOptional(tag.toUpperCase());
                if(result.isEmpty()) {
                    Tag savedTag = tagCommandPort.save(tag);
                    tagList.add(savedTag);
                } else {
                    tagList.add(result.get());
                }
            }
        }
        return tagList;
    }
}
