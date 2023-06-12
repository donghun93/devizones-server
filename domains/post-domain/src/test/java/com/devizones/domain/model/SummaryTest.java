package com.devizones.domain.model;

import com.devizones.domain.post.exception.PostException;
import com.devizones.domain.post.model.Summary;
import com.devizones.util.RandomStringFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class SummaryTest {
    @Test
    @DisplayName("요약이 최대 길이를 초과하는 경우 PostException이 발생하는지 테스트합니다")
    void testCreate_MaxLengthExceeded() {
        // given
        String summary = RandomStringFactory.generateRandomString(Summary.MAX_SUMMARY_LENGTH + 1);

        // when, then
        Assertions.assertThrows(PostException.class, () -> {
            Summary.create(summary);
        });
    }

    @Test
    @DisplayName("요약이 최대 길이 이하인 경우 요약이 생성되는지 테스트합니다")
    void testCreate_ValidSummary() {
        // given
        String summary = RandomStringFactory.generateRandomString(Summary.MAX_SUMMARY_LENGTH);

        // when
        Summary createdSummary = Summary.create(summary);

        // then
        Assertions.assertEquals(summary, createdSummary.getValue());
    }

    @DisplayName("요약이 null인 경우 요약이 생성되는지 테스트합니다")
    @ParameterizedTest
    @NullAndEmptySource
    void testCreate_NullSummary(String emptySummary) {
        // given

        // when
        Summary createdSummary = Summary.create(emptySummary);

        // then
        Assertions.assertNull(createdSummary.getValue());
    }
}