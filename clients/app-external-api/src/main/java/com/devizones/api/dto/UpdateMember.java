package com.devizones.api.dto;

import com.devizones.application.member.dto.MemberIntroduceUpdateCommand;
import com.devizones.application.member.dto.MemberNicknameUpdateCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UpdateMember {
    @Getter
    public static class NicknameRequest {

        @Schema(
                description = "닉네임",
                example = "hello",
                defaultValue = "nickname",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "nickname은 필수입니다.")
        private String nickname;

        public MemberNicknameUpdateCommand toCommand() {
            return new MemberNicknameUpdateCommand(nickname);
        }
    }

    public static class NicknameResponse {
        public static BaseResponse<NicknameResponse> success() {
            return BaseResponse.success();
        }
    }


    @Getter
    public static class IntroduceRequest {
        @Schema(
                description = "자기소개",
                example = "안녕하세요",
                defaultValue = "introduce",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "introduce은 필수입니다.")
        private String introduce;

        public MemberIntroduceUpdateCommand toCommand() {
            return new MemberIntroduceUpdateCommand(introduce);
        }
    }

    public static class IntroduceResponse {
        public static BaseResponse<IntroduceResponse> success() {
            return BaseResponse.success();
        }
    }


    public static class Profile {

        public static class Response {
            public static BaseResponse<UpdateMember.Profile.Response> success() {
                return BaseResponse.success();
            }
        }
    }
}
