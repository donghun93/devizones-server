package com.devizones.post.rds.entity;

import com.devizones.domain.post.model.Image;
import com.devizones.domain.post.model.Post;
import com.devizones.domain.post.model.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long writerId;
    private String title;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String contents;


    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<ImageEntity> images = new ArrayList<>();

    private String summary;
    private boolean visible;
    private boolean deleted;
    private Long likeCount;

    @Version
    private Long version;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTagEntity> postTags = new ArrayList<>();

    @Builder
    private PostEntity(Long id, Long writerId, String title, String contents, boolean visible, String summary, boolean deleted, Long likeCount, List<ImageEntity> images) {
        this.id = id;
        this.writerId = writerId;
        this.title = title;
        this.contents = contents;
        this.visible = visible;
        this.summary = summary;
        this.deleted = deleted;
        this.likeCount = likeCount;
        this.images = images;
    }

    public void update(Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.visible = post.isVisible();
        this.summary = post.getSummary();

        this.images.clear();
        for (Image image : post.getImages().getImages()) {
            this.images.add(ImageEntity.builder()
                                       .path(image.getPath())
                                       .thumbnail(image.isThumbnail())
                                       .build());
        }

        this.tagClear();
        for (Tag tag : post.getTags()) {
            this.addTag(TagEntity.builder()
                                 .id(tag.getId())
                                 .name(tag.getValue())
                                 .build());
        }
    }

    public void delete() {
        if (!this.deleted) {
            this.deleted = true;
            setDeletedAt(LocalDateTime.now());
        }
    }

    public void tagClear() {
        this.postTags.clear();
    }

    public void addTag(TagEntity tagEntity) {
        PostTagEntity postTagEntity = createPostTagEntity(tagEntity);
        postTags.add(postTagEntity);
    }

    private PostTagEntity createPostTagEntity(TagEntity tagEntity) {
        return PostTagEntity.builder()
                            .post(this)
                            .tag(tagEntity)
                            .build();
    }

    public void like() {
        this.likeCount++;
    }

    public void unlike() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
