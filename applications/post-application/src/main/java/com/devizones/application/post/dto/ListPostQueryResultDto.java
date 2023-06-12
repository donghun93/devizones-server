package com.devizones.application.post.dto;

import java.util.List;

public record ListPostQueryResultDto(Long key, List<PostListViewDto> items) {

}