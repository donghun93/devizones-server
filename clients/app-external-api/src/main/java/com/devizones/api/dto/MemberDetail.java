package com.devizones.api.dto;

import com.devizones.application.member.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class MemberDetail {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String email;
        private String nickname;
        private String profile;
        private String introduce;
        private String social;

        private Response(Long id, String email, String nickname, String profile, String introduce, String social) {
            this.id = id;
            this.email = email;
            this.nickname = nickname;
            this.profile = profile;
            this.introduce = introduce;
            this.social = social;
        }

        public static BaseResponse<MemberDetail.Response> success(MemberDto memberDto, String s3) {
            return BaseResponse.success(new MemberDetail.Response(
                    memberDto.id(),
                    memberDto.email(),
                    memberDto.nickname(),
                    s3 + memberDto.profile(),
                    memberDto.introduce(),
                    memberDto.social()
            ));
        }
    }
}
