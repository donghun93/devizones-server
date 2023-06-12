package com.devizones.post.rds.mapper;

import com.devizones.domain.post.model.Image;
import com.devizones.domain.post.model.Post;
import com.devizones.domain.post.model.Tag;
import com.devizones.post.rds.entity.ImageEntity;
import com.devizones.post.rds.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class PostMapper {
    private final TagMapper tagMapper;

    public Post toDomainModel(PostEntity postEntity) {
        return Post.builder()
                   .id(postEntity.getId())
                   .writerId(postEntity.getWriterId())
                   .title(postEntity.getTitle())
                   .contents(postEntity.getContents())
                   .images(postEntity.getImages()
                                     .stream()
                                     .map(ie ->
                                             Image.builder()
                                                  .id(ie.getId())
                                                  .path(ie.getPath())
                                                  .thumbnail(ie.isThumbnail())
                                                  .build())
                                     .collect(toList()))
                   .tags(
                           postEntity.getPostTags()
                                     .stream()
                                     .map(te -> tagMapper.toDomainModel(te.getTag()))
                                     .collect(toList())
                   )
                   .likeCount(postEntity.getLikeCount())
                   .summary(postEntity.getSummary())
                   .visible(postEntity.isVisible())
                   .deleted(postEntity.isDeleted())
                   .createdAt(postEntity.getCreatedAt())
                   .modifiedAt(postEntity.getModifiedAt())
                   .build();
    }

    public PostEntity toEntity(Post post) {
        PostEntity postEntity = PostEntity.builder()
                                          .id(post.getId())
                                          .writerId(post.getWriterId())
                                          .title(post.getTitle())
                                          .contents(post.getContents())
                                          .visible(post.isVisible())
                                          .summary(post.getSummary())
                                          .deleted(post.isDeleted())
                                          .likeCount(post.getLikeCount())
                                          .images(post.getImages()
                                                      .getImages()
                                                      .stream()
                                                      .map(i -> ImageEntity.builder()
                                                                           .id(i.getId())
                                                                           .path(i.getPath())
                                                                           .thumbnail(i.isThumbnail())
                                                                           .build())
                                                      .collect(toList())
                                          )
                                          .build();

        for (Tag tag : post.getTags()) {
            postEntity.addTag(tagMapper.toEntity(tag));
        }
        return postEntity;
    }
}
