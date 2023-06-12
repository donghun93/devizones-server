package com.devizones.api;

import com.devizones.api.dto.*;
import com.devizones.application.post.dto.*;
import com.devizones.application.post.service.PostCommandService;
import com.devizones.application.post.service.PostQueryService;
import com.devizones.image.s3.adapter.config.S3Properties;
import com.devizones.web.core.token.model.CurrentMember;
import com.devizones.web.core.token.model.MemberAccount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.isNull;

@Tag(name = "post", description = "게시글 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostApiController {

    private final PostQueryService postQueryService;
    private final PostCommandService postCommandService;
    private final S3Properties s3Properties;

    @Operation(
            summary = "게시글 작성",
            description = "게시글을 작성 할 수 있습니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<CreatePost.Response> register(
            @CurrentMember MemberAccount memberAccount,
            @Valid @ModelAttribute CreatePost.Request request) {
        Long postId = postCommandService.register(
                memberAccount.getId(),
                request.toCommand()
        );
        return CreatePost.Response.success(postId);
    }

    @Operation(
            summary = "게시글 이미지 업로드",
            description = "게시글 이미지를 업로드할 수 있습니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @PostMapping(value = "/images",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<PostImageUpload.Response> upload(
            @RequestPart MultipartFile image) {
        InputStream inputStream = null;
        try {
            inputStream = image.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String filePath = postCommandService.upload(image.getOriginalFilename(), inputStream);
        return PostImageUpload.Response.success(filePath, s3Properties.getPostAbsolutePath());
    }

    @Operation(
            summary = "게시글 목록 조회",
            description = "게시글 목록을 조회할 수 있습니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @GetMapping
    public BaseResponse<ListPost.Response> getList(@ModelAttribute ListPost.ListPostRequest listPostRequest) {
        if (listPostRequest.getSize() == null) listPostRequest.setSize(20);
        if (isNull(listPostRequest.getKey())) listPostRequest.setKey(-1L);

        ListPostQueryResultDto result = postQueryService.query(new CursorBaseCommand(listPostRequest.getKey(), listPostRequest.getSize()));
        return ListPost.Response.success(result, s3Properties.getProfileAbsolutePath(), s3Properties.getPostAbsolutePath());
    }

    @Operation(
            summary = "게시글 목록 검색",
            description = "검색어를 사용해서 게시글 목록을 조회합니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @GetMapping("/search")
    public BaseResponse<ListPost.Response> getSearchList(@ModelAttribute ListSearchPost.ListSearchPostRequest listSearchPostRequest) {
        if (listSearchPostRequest.getSize() == null) listSearchPostRequest.setSize(20);
        if (isNull(listSearchPostRequest.getKey())) listSearchPostRequest.setKey(-1L);

        ListPostQueryResultDto result = postQueryService.query(new CursorBaseSearchCommand(
                listSearchPostRequest.getKey(), listSearchPostRequest.getSize(), listSearchPostRequest.getKeyword())
        );

        return ListPost.Response.success(result, s3Properties.getProfileAbsolutePath(), s3Properties.getPostAbsolutePath());
    }

    @Operation(
            summary = "게시글 상세 조회",
            description = "검색어를 사용해서 게시글 목록을 조회합니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @GetMapping("/{postId}")
    public BaseResponse<DetailPost.Response> getDetail(
            @CurrentMember MemberAccount memberAccount,
            @PathVariable Long postId) {

        Long memberId = (memberAccount != null) ? memberAccount.getId() : null;
        PostDetailDto detail = postQueryService.detail(postId, memberId);
        if(memberAccount != null) {
            boolean writeAble = memberAccount.getId().equals(detail.getWriterId());
            detail.setWrite(writeAble);
        }

        return DetailPost.Response.success(detail, s3Properties.getProfileAbsolutePath());
    }

    @Operation(
            summary = "게시글 삭제",
            description = "게시글을 삭제합니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<DeletePost.Response> deletePost(
            @CurrentMember MemberAccount memberAccount,
            @PathVariable Long postId) {
        Long deletedId = postCommandService.delete(postId, memberAccount.getId());
        return DeletePost.Response.success(deletedId);
    }

    @Operation(
            summary = "게시글 좋아요",
            description = "'좋아요' 수를 증가시킵니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @PostMapping("/{postId}/like")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<LikePost.LikeResponse> like(
            @CurrentMember MemberAccount memberAccount,
            @PathVariable Long postId) {
        postCommandService.like(postId, memberAccount.getId());
        return LikePost.LikeResponse.success(postId);
    }

    @Operation(
            summary = "게시글 좋아요 해제",
            description = "'좋아요' 수를 감소시킵니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @DeleteMapping("/{postId}/unlike")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<LikePost.UnLikeResponse> unlike(
            @CurrentMember MemberAccount memberAccount,
            @PathVariable Long postId) {
        postCommandService.unLike(postId, memberAccount.getId());
        return LikePost.UnLikeResponse.success(postId);
    }

    @Operation(
            summary = "게시글 수정",
            description = "게시글을 수정할 수 있습니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<EditPost.Response> edit(
            @CurrentMember MemberAccount memberAccount,
            @PathVariable Long postId,
            @Valid @ModelAttribute EditPost.Request request) {
        postCommandService.edit(postId, memberAccount.getId(), request.toCommand());
        return EditPost.Response.success(postId);
    }
}
