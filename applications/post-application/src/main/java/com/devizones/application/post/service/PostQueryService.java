package com.devizones.application.post.service;

import com.devizones.application.member.port.MemberLoadPort;
import com.devizones.application.post.dto.*;
import com.devizones.application.post.port.PostLikeQueryPort;
import com.devizones.application.post.port.PostQueryPort;
import com.devizones.domain.member.model.Member;
import com.devizones.domain.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {

    private final PostQueryPort postQueryPort;
    private final PostLikeQueryPort postLikeQueryPort;
    private final MemberLoadPort memberLoadPort;

    public PostDetailDto detail(Long postId, Long memberId) {
        PostDetailDto postDetailDto = postQueryPort.findByIdDetailDto(postId);

        if(!isNull(memberId) && postLikeQueryPort.existByPostIdAndMemberId(postId, memberId)) {
            postDetailDto.setLike(true);
        }

        Member member = memberLoadPort.findById(postDetailDto.getWriterId());
        postDetailDto.setMember(member);
        return postDetailDto;
    }

    public ListPostQueryResultDto query(CursorBaseCommand command) {
        List<PostListDto> postDto = postQueryPort.load(command.key(), command.size());

        Map<Long, Member> members = getMemberMap(postDto);

        var nextKey = postDto.stream()
                             .mapToLong(PostListDto::postId)
                             .min()
                             .orElse(-1L);


        return new ListPostQueryResultDto(
                nextKey,
                postDto.stream()
                       .map(
                               p -> new PostListViewDto(
                                       p.postId(),
                                       p.writerId(),
                                       p.title(),
                                       p.summary(),
                                       p.createdAt(),
                                       members.get(p.writerId())
                                              .getNickname()
                                              .getValue(),
                                       members.get(p.writerId())
                                              .getProfile()
                                              .getFileName(),
                                       p.thumbNail(),
                                       p.like()
                               )
                       )
                       .collect(Collectors.toList()));
    }

    // 작성한 글
    public Page<PostMyPageUIDto> writtenPost(Pageable pageable, Long writerId) {
        return postQueryPort.getWrittenPost(pageable, writerId);
    }


    // 좋아요
    public Page<PostMyPageUIDto> likePost(Pageable pageable, Long writerId) {
        return postQueryPort.likePost(pageable, writerId);
    }

    // 태그


    public ListPostQueryResultDto query(CursorBaseSearchCommand command) {
        List<PostListDto> postDto = postQueryPort.load(command.key(), command.size(), command.keyword());

        Map<Long, Member> members = getMemberMap(postDto);

        var nextKey = postDto.stream()
                             .mapToLong(PostListDto::postId)
                             .min()
                             .orElse(-1L);


        return new ListPostQueryResultDto(
                nextKey,
                postDto.stream()
                       .map(
                               p -> new PostListViewDto(
                                       p.postId(),
                                       p.writerId(),
                                       p.title(),
                                       p.summary(),
                                       p.createdAt(),
                                       members.get(p.writerId())
                                              .getNickname()
                                              .getValue(),
                                       members.get(p.writerId())
                                              .getProfile()
                                              .getFileName(),
                                       p.thumbNail(),
                                       p.like()
                               )
                       )
                       .collect(Collectors.toList()));
    }

    private Map<Long, Member> getMemberMap(List<PostListDto> postDto) {
        Set<Long> memberIds = postDto.stream()
                                     .map(PostListDto::writerId)
                                     .collect(Collectors.toSet());

        return memberLoadPort.findByIdIn(memberIds);
    }
}
