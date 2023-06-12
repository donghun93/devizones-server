package com.devizones.domain.post.model;

import com.devizones.domain.post.dto.CreatePostDomainModelDto;
import com.devizones.domain.post.dto.EditPostDomainModelDto;
import com.devizones.domain.post.exception.PostErrorCode;
import com.devizones.domain.post.exception.PostException;
import com.devizones.domain.post.service.ContentsImageAnalyzeDomainService;
import com.devizones.domain.post.service.RandomFileNameGenerateDomainService;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.devizones.commons.StringUtils.hasText;
import static com.devizones.domain.post.exception.PostErrorCode.POST_NOT_OWNER;


@Getter
public class Post {

    private final Long id;
    private final Long writerId;
    private Title title;
    private Contents contents;
    private ImageCollection images;
    private TagCollection tags;
    private Summary summary;
    private boolean visible;
    private boolean deleted;
    private Long likeCount;

    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    private Post(Long id, Long writerId, String title, String contents, List<Image> images, List<Tag> tags, String summary, boolean visible, boolean deleted,
                 Long likeCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;

        if (writerId == null) {
            throw new PostException(PostErrorCode.WRITER_EMPTY);
        }

        this.writerId = writerId;
        this.title = Title.create(title);
        this.contents = Contents.create(contents);
        this.images = ImageCollection.create(images);
        this.tags = TagCollection.create(tags);
        this.summary = Summary.create(summary);
        this.visible = visible;
        this.deleted = deleted;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }


    private boolean isOwner(Long writerId) {
        return this.writerId.equals(writerId);
    }
    // 게시글 삭제
    public void delete(Long writerId) {

        if(!isOwner(writerId)) {
            throw new PostException(POST_NOT_OWNER);
        }

        if (this.deleted) {
            throw new PostException(PostErrorCode.POST_ALREADY_DELETED);
        }

        this.deleted = true;
    }

    // 좋아요
    public void like() {
        likeCount++;
    }

    // 좋아요 해제
    public void unLike() {
        if (likeCount > 0) {
            likeCount--;
        }
    }

    public void edit(EditPostDomainModelDto editPostDomainModelDto) {
        List<Image> insertImages = ContentsImageAnalyzeDomainService.getImages(
                editPostDomainModelDto.contents());

        if (hasText(editPostDomainModelDto.thumbnailFilename())) {
            String randomFileName = RandomFileNameGenerateDomainService.generate(editPostDomainModelDto.thumbnailFilename());

            Image thumbnail = Image.create(randomFileName, true);
            insertImages.add(0, thumbnail);
        } else {
            if (!insertImages.isEmpty()) {
                insertImages.get(0)
                            .setThumbnail(true);
            }
        }

        this.title = Title.create(editPostDomainModelDto.title());
        this.contents = Contents.create(editPostDomainModelDto.contents());
        this.images = ImageCollection.create(insertImages);
        this.tags = TagCollection.create(editPostDomainModelDto.tags());
        this.summary = Summary.create(editPostDomainModelDto.summary());
        this.visible = editPostDomainModelDto.visible();
    }


    public String getThumbnail() {
        if (images.getImages().isEmpty()) {
            return null;
        } else {
            Optional<Image> thumbnail = images.getImages()
                                              .stream()
                                              .filter(Image::isThumbnail)
                                              .findFirst();

            return thumbnail.map(Image::getPath)
                            .orElse(null);
        }
    }

    public List<Tag> getTags() {
        return tags.getTags();
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getContents() {
        return contents.getValue();
    }

    public String getSummary() {

        if(summary.isEmpty()) {
            return getPlainContents(this.contents.getValue());
        }

        return summary.getValue();
    }

    private String getPlainContents(String markdown) {
        String patternImageTags = "!\\[[^\\]]*\\]\\([^)]+\\)";
        String noImageTags = markdown.replaceAll(patternImageTags, "");

        String replacedWithSpaces = noImageTags.replaceAll("\n|\r\n", " ").trim();

        // Remove leading hashes
        replacedWithSpaces = replacedWithSpaces.replaceAll("^#", "");

        // Replace two or more spaces with a single space
        replacedWithSpaces = replacedWithSpaces.replaceAll(" +", " ").trim();

        // If the string is longer than 50 characters, keep only the first 50
        if (replacedWithSpaces.length() > 50) {
            replacedWithSpaces = replacedWithSpaces.substring(0, 50);
        }

        return replacedWithSpaces;
    }


    public static Post create(CreatePostDomainModelDto createPostDomainModelDto) {

        List<Image> insertImages = ContentsImageAnalyzeDomainService.getImages(
                createPostDomainModelDto.contents());

        if (hasText(createPostDomainModelDto.thumbnail())) {
            String randomFileName = RandomFileNameGenerateDomainService.generate(createPostDomainModelDto.thumbnail());

            Image thumbnail = Image.create(randomFileName, true);
            insertImages.add(0, thumbnail);
        } else {
            if (!insertImages.isEmpty()) {
                insertImages.get(0)
                            .setThumbnail(true);
            }
        }

        return Post.builder()
                   .id(null)
                   .writerId(createPostDomainModelDto.writerId())
                   .title(createPostDomainModelDto.title())
                   .contents(createPostDomainModelDto.contents())
                   .tags(createPostDomainModelDto.tags())
                   .images(insertImages)
                   .summary(createPostDomainModelDto.summary())
                   .visible(createPostDomainModelDto.visible())
                   .deleted(false)
                   .likeCount(0L)
                   .build();
    }
}
