package com.devizones.domain.model;

import com.devizones.domain.post.dto.CreatePostDomainModelDto;
import com.devizones.domain.post.exception.PostException;
import com.devizones.domain.post.model.Post;
import com.devizones.domain.post.model.Summary;
import com.devizones.domain.post.model.Tag;
import com.devizones.domain.post.model.Title;
import com.devizones.util.RandomStringFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.devizones.domain.post.exception.PostErrorCode.POST_ALREADY_DELETED;
import static com.devizones.domain.post.exception.PostErrorCode.WRITER_EMPTY;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostTest {
    @Test
    @DisplayName("유효한 입력으로 포스트 객체를 생성하는지 테스트합니다")
    void testCreate_ValidPost() {
        // given
        final Long writerId = 1L;
        final String thumbnail = "thumbnail.png";
        final boolean visible = true;

        // when
        Post post = Post.builder()
                        .id(null)
                        .writerId(writerId)
                        .title(RandomStringFactory.generateRandomString(Title.MIN_LENGTH, Title.MAX_LENGTH))
                        .contents(RandomStringFactory.generateRandomString(1, 100))
                        .thumbnail(thumbnail)
                        .tags(null)
                        .summary(RandomStringFactory.generateRandomString(Summary.MAX_SUMMARY_LENGTH))
                        .visible(visible)
                        .likeCount(0L)
                        .build();

        // then
        assertThat(post.getId()).isNull();
        assertThat(post.getWriterId()).isEqualTo(writerId);
        assertThat(post.getTitle()).isNotNull();
        assertThat(post.getContents()).isNotNull();
        assertThat(post.getThumbnail()).isEqualTo(thumbnail);
        assertTrue(post.getTags()
                       .isEmpty());
        assertThat(post.getSummary()).isNotNull();
        assertTrue(post.isVisible());
        assertThat(post.getLikeCount()).isZero();
    }

    @DisplayName("게시글 도메인 생성 테스트1")
    @Test
    void createTest1() {
        // given
        final Long writerId = 1L;
        final String title = "title";
        final String contents = "### 본문입니다.\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/1bec85c6d-3185-462c-b81d-ace7d795b7dd.png)" + "'\n" +
                "abcdefg\n" +
                "### 제목입니다.\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/2bec85c6d-3185-462c-b81d-ace7d795b7dd.png)\n" +
                "---\n" +
                "가나다라마바사";
        final List<Tag> tags = List.of(
                Tag.builder()
                   .id(1L)
                   .tagName("Spring Security")
                   .build()
        );
        final boolean visible = true;
        CreatePostDomainModelDto domainModelDto = new CreatePostDomainModelDto(
                writerId,
                title,
                contents,
                tags,
                null,
                null,
                visible
        );

        // when
        Post post = Post.create(domainModelDto);

        // then
        assertThat(post.getId()).isNull();
        assertThat(post.getWriterId()).isEqualTo(writerId);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContents()).isEqualTo(contents);
        assertThat(post.getTags()).isNotNull()
                                  .isNotEmpty()
                                  .hasSize(1);
        assertThat(post.getSummary()).isNull();
        assertThat(post.getThumbnail()).isEqualTo("1bec85c6d-3185-462c-b81d-ace7d795b7dd.png");
        assertThat(post.isVisible()).isTrue();
    }

    @DisplayName("게시글 도메인 생성 테스트1")
    @Test
    void createTest2() {
        // given
        final Long writerId = 1L;
        final String title = "title";
        final String contents = "### 본문입니다.\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/1bec85c6d-3185-462c-b81d-ace7d795b7dd.png)" + "'\n" +
                "abcdefg\n" +
                "### 제목입니다.\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/2bec85c6d-3185-462c-b81d-ace7d795b7dd.png)\n" +
                "---\n" +
                "가나다라마바사";
        final boolean visible = true;

        CreatePostDomainModelDto domainModelDto = new CreatePostDomainModelDto(
                writerId,
                title,
                contents,
                null,
                null,
                null,
                visible
        );

        // when
        Post post = Post.create(domainModelDto);

        // then
        assertThat(post.getId()).isNull();
        assertThat(post.getWriterId()).isEqualTo(writerId);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContents()).isEqualTo(contents);
        assertThat(post.getTags()).isNotNull()
                                  .isEmpty();
        assertThat(post.getSummary()).isNull();
        assertThat(post.getThumbnail()).isEqualTo("1bec85c6d-3185-462c-b81d-ace7d795b7dd.png");
        assertThat(post.isVisible()).isTrue();
    }


    @Test
    @DisplayName("유효한 입력으로 포스트 객체를 생성하는지 테스트합니다")
    void testCreate_ValidPost2() {
        // given
        final Long writerId = 1L;
        final boolean visible = true;
        final List<Tag> tags = List.of(
                Tag.builder()
                   .id(1L)
                   .tagName("Spring Boot")
                   .build(),
                Tag.builder()
                   .id(2L)
                   .tagName("Spring Boot1")
                   .build(),
                Tag.builder()
                   .id(3L)
                   .tagName("Spring Boot2")
                   .build()
        );
        final String contents = "### 본문입니다.\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/1bec85c6d-3185-462c-b81d-ace7d795b7dd.png)" + "'\n" +
                "abcdefg\n" +
                "### 제목입니다.\n" +
                "![alt text](https://winter-blog-bucket.s3.ap-northeast-2.amazonaws.com/post/2bec85c6d-3185-462c-b81d-ace7d795b7dd.png)\n" +
                "---\n" +
                "가나다라마바사";

        // when
        Post post = Post.builder()
                        .id(null)
                        .writerId(writerId)
                        .title(RandomStringFactory.generateRandomString(Title.MIN_LENGTH, Title.MAX_LENGTH))
                        .contents(contents)
                        .thumbnail(null)
                        .tags(tags)
                        .summary(null)
                        .visible(visible)
                        .likeCount(0L)
                        .build();

        // then
        assertThat(post.getId()).isNull();
        assertThat(post.getWriterId()).isEqualTo(writerId);
        assertThat(post.getTitle()).isNotNull();
        assertThat(post.getContents()).isNotNull();
        assertThat(post.getThumbnail()).isEqualTo("1bec85c6d-3185-462c-b81d-ace7d795b7dd.png");
        assertFalse(post.getTags()
                        .isEmpty());
        assertThat(post.getTags())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .extracting("id", "value")
                .contains(
                        tuple(1L, "Spring Boot"),
                        tuple(2L, "Spring Boot1"),
                        tuple(3L, "Spring Boot2")
                );
        assertThat(post.getSummary()).isNull();
        assertTrue(post.isVisible());
        assertThat(post.getLikeCount()).isZero();
    }


    @Test
    void testCreate_WriterIdNull() {
        // given
        final String thumbnail = "thumbnail.png";
        final boolean visible = true;

        // when & then
        assertThatThrownBy(() -> Post.builder()
                                     .id(null)
                                     .writerId(null)
                                     .title(RandomStringFactory.generateRandomString(Title.MIN_LENGTH, Title.MAX_LENGTH))
                                     .contents(RandomStringFactory.generateRandomString(1, 100))
                                     .thumbnail(thumbnail)
                                     .tags(null)
                                     .summary(RandomStringFactory.generateRandomString(Summary.MAX_SUMMARY_LENGTH))
                                     .visible(visible)
                                     .build())
                .isInstanceOf(PostException.class)
                .hasFieldOrPropertyWithValue("errorCode", WRITER_EMPTY);
    }

    @Test
    void delete_alreadyDeletedPost_throwsPostException() {
        // given
        Post post = Post.builder()
                        .id(null)
                        .writerId(1L)
                        .title(RandomStringFactory.generateRandomString(Title.MIN_LENGTH, Title.MAX_LENGTH))
                        .contents("contents")
                        .tags(null)
                        .summary(null)
                        .visible(true)
                        .likeCount(0L)
                        .build();
//        post.delete();
//
//        // when, then
//        assertThatThrownBy(post::delete)
//                .isInstanceOf(PostException.class)
//                .hasFieldOrPropertyWithValue("errorCode", POST_ALREADY_DELETED);
    }

    @Test
    void like_increaseLikeCount() {
        // given
        Post post = Post.builder()
                        .id(null)
                        .writerId(1L)
                        .title(RandomStringFactory.generateRandomString(Title.MIN_LENGTH, Title.MAX_LENGTH))
                        .contents("contents")
                        .thumbnail(null)
                        .tags(null)
                        .summary(null)
                        .visible(true)
                        .likeCount(0L)
                        .build();

        // when
        post.like();

        // then
        assertThat(post.getLikeCount()).isEqualTo(1L);
    }

    @Test
    @DisplayName("UnLike: Decrease Like Count")
    void unLike_decreaseLikeCount() {
        // given
        Post post = Post.builder()
                        .id(null)
                        .writerId(1L)
                        .title(RandomStringFactory.generateRandomString(Title.MIN_LENGTH, Title.MAX_LENGTH))
                        .contents("contents")
                        .thumbnail(null)
                        .tags(null)
                        .summary(null)
                        .visible(true)
                        .likeCount(0L)
                        .build();
        post.like();

        // when
        post.unLike();

        // then
        assertThat(post.getLikeCount()).isEqualTo(0L);
    }
}