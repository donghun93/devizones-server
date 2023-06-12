package com.devizones.domain.model;

import com.devizones.domain.post.exception.PostException;
import com.devizones.domain.post.model.Title;
import com.devizones.util.RandomStringFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.devizones.domain.post.exception.PostErrorCode.TITLE_EMPTY;
import static com.devizones.domain.post.exception.PostErrorCode.TITLE_NOT_VALID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TitleTest {

    @Test
    void testSetTitle_ValidTitle() {
        // given
        String validTitle = RandomStringFactory.generateRandomString(Title.MIN_LENGTH, Title.MIN_LENGTH);

        // when
        Title title = Title.builder()
                           .title(validTitle)
                           .build();

        // then
        assertThat(title.getValue()).isEqualTo(validTitle);
    }

    @Test
    void testSetMinLengthTitle_ValidTitle() {
        // given
        String validTitle = RandomStringFactory.generateRandomString(Title.MIN_LENGTH);

        // when
        Title title = Title.builder()
                           .title(validTitle)
                           .build();

        // then
        assertThat(title.getValue()).isEqualTo(validTitle);
    }

    @Test
    void testSetMaxLengthTitle_ValidTitle() {
        // given
        String validTitle = RandomStringFactory.generateRandomString(Title.MAX_LENGTH);

        // when
        Title title = Title.builder()
                           .title(validTitle)
                           .build();

        // then
        assertThat(title.getValue()).isEqualTo(validTitle);
    }


    @ParameterizedTest
    @NullAndEmptySource
    void testSetTitle_EmptyTitle(String emptyTitle) {
        // given

        // when, then
        assertThatThrownBy(() ->
                Title.builder()
                     .title(emptyTitle)
                     .build()
        ).isInstanceOf(PostException.class)
         .hasFieldOrPropertyWithValue("errorCode", TITLE_EMPTY);
    }

    @Test
    void testSetTitle_TooShortTitle() {
        // given
        String shortTitle = RandomStringFactory.generateRandomString(Title.MIN_LENGTH - 1);

        // when, then
        assertThatThrownBy(() -> {
            Title.builder()
                 .title(shortTitle)
                 .build();
        }).isInstanceOf(PostException.class)
          .hasFieldOrPropertyWithValue("errorCode", TITLE_EMPTY);
    }

    @Test
    void testSetTitle_TooLongTitle() {
        // given
        String longTitle = RandomStringFactory.generateRandomString(Title.MAX_LENGTH + 1);

        // when, then
        assertThatThrownBy(() -> {
            Title.builder()
                 .title(longTitle)
                 .build();
        }).isInstanceOf(PostException.class)
          .hasFieldOrPropertyWithValue("errorCode", TITLE_NOT_VALID);
    }
}