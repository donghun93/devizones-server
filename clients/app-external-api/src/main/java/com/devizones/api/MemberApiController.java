package com.devizones.api;

import com.devizones.api.dto.BaseResponse;
import com.devizones.api.dto.MemberDetail;
import com.devizones.api.dto.MyPage;
import com.devizones.api.dto.UpdateMember;
import com.devizones.application.member.dto.MemberDto;
import com.devizones.application.member.dto.MemberProfileUpdateCommand;
import com.devizones.application.member.service.MemberCommandService;
import com.devizones.application.member.service.MemberQueryService;
import com.devizones.application.post.dto.PostMyPageUIDto;
import com.devizones.application.post.service.PostQueryService;
import com.devizones.image.s3.adapter.config.S3Properties;
import com.devizones.web.core.token.model.CurrentMember;
import com.devizones.web.core.token.model.MemberAccount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "member", description = "회원 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberApiController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final PostQueryService postQueryService;
    private final S3Properties s3Properties;

    @Operation(
            summary = "회원정보 상세 조회",
            description = "회원정보를 조회 할 수 있습니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @GetMapping("/detail")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<MemberDetail.Response> detail(@CurrentMember MemberAccount memberAccount) {
        MemberDto member = memberQueryService.getMember(memberAccount.getId());
        return MemberDetail.Response.success(member, s3Properties.getProfileAbsolutePath());
    }

    @Operation(
            summary = "회원 닉네임 수정",
            description = "회원 닉네임을 수정할 수 있습니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @PatchMapping("/nickname")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<UpdateMember.NicknameResponse> updateNickname(
            @CurrentMember MemberAccount memberAccount,
            @Valid @RequestBody UpdateMember.NicknameRequest nicknameRequest) {
        memberCommandService.updateNickname(memberAccount.getId(), nicknameRequest.toCommand());
        return UpdateMember.NicknameResponse.success();
    }

    @Operation(
            summary = "회원 자기소개 수정",
            description = "회원 자기소개 수정할 수 있습니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @PatchMapping("/introduce")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<UpdateMember.IntroduceResponse> updateIntroduce(
            @CurrentMember MemberAccount memberAccount,
            @Valid @RequestBody UpdateMember.IntroduceRequest introduceRequest) {
        memberCommandService.updateIntroduce(memberAccount.getId(), introduceRequest.toCommand());
        return UpdateMember.IntroduceResponse.success();
    }

    @Operation(
            summary = "회원 프로필 등록",
            description = "회원 프로필을 등록할 수 있습니다. 기존 프로필 이미지는 제거됩니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @PostMapping(value  = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<UpdateMember.Profile.Response> registerProfile(
            @CurrentMember MemberAccount memberAccount,
            @RequestPart MultipartFile profile) {

        try {
            memberCommandService.updateProfile(
                    memberAccount.getId(),
                    new MemberProfileUpdateCommand(profile.getOriginalFilename(), profile.getInputStream())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return UpdateMember.Profile.Response.success();
    }

    @Operation(
            summary = "내 작성글 조회",
            description = "사용자가 작성한 글을 조회합니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @GetMapping("/written")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<Page<MyPage.Written.Response>> getWrittenPostPage(
            @CurrentMember MemberAccount memberAccount,
            @PageableDefault Pageable pageable
    ) {
        Page<PostMyPageUIDto> postMyPageUIDto = postQueryService.writtenPost(pageable, memberAccount.getId());
        return MyPage.Written.Response.success(postMyPageUIDto, s3Properties.getProfileAbsolutePath());
    }

    @Operation(
            summary = "내 좋아요 조회",
            description = "사용자가 좋아요한 글을 조회합니다.",
            security = {@SecurityRequirement(name = "bearer-key")}
    )
    @GetMapping("/like")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<Page<MyPage.Written.Response>> getLikePostPage(
            @CurrentMember MemberAccount memberAccount,
            @PageableDefault Pageable pageable
    ) {
        Page<PostMyPageUIDto> postMyPageUIDto = postQueryService.likePost(pageable, memberAccount.getId());
        return MyPage.Written.Response.success(postMyPageUIDto, s3Properties.getProfileAbsolutePath());
    }

}
