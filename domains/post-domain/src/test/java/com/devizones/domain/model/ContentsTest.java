package com.devizones.domain.model;

import com.devizones.domain.post.exception.PostException;
import com.devizones.domain.post.model.Contents;
import com.devizones.util.RandomStringFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.devizones.domain.post.exception.PostErrorCode.CONTENTS_EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentsTest {
    @Test
    void testSetContents_ValidContents() {
        // given
        String validContents = RandomStringFactory.generateRandomString(100);

        // when
        Contents contents = Contents.builder()
                                    .contents(validContents)
                                    .build();

        // then
        assertThat(contents.getValue()).isEqualTo(validContents);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testSetContents_EmptyContents(String emptyContents) {
        // given

        // when, then
        assertThatThrownBy(() -> {
            Contents.builder()
                    .contents(emptyContents)
                    .build();
        }).isInstanceOf(PostException.class)
                .hasFieldOrPropertyWithValue("errorCode", CONTENTS_EMPTY);
    }

    @Test
    void testCreate_ValidContents() {
        // given
        String validContents = "Valid Contents";

        // when
        Contents contents = Contents.create(validContents);

        // then
        assertThat(contents.getValue()).isEqualTo(validContents);
    }
}