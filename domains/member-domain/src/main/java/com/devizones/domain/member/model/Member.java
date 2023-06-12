package com.devizones.domain.member.model;


import com.devizones.commons.event.Events;
import com.devizones.domain.member.dto.CreateMemberDomainModelDto;
import com.devizones.domain.member.event.ProfileUpdatedEvent;
import com.devizones.domain.member.service.RandomNicknameGenerateDomainService;
import com.devizones.domain.member.service.RandomProfileGenerateDomainService;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private Email email;
    private Nickname nickname;
    private Profile profile;
    private Introduce introduce;
    private boolean deleted;
    private Social social;

    @Builder
    private Member(Long id, String email, String nickname, String profile, String introduce, boolean deleted, String social) {
        this.id = id;
        this.email = Email.create(email);
        this.nickname = Nickname.create(nickname);
        this.profile = Profile.create(profile);
        this.introduce = Introduce.create(introduce);
        this.social = Social.convert(social);
        this.deleted = deleted;
    }

    // 프로필 변경
    public void updateProfile(String fileName) {
        Profile originalProfile = this.profile;
        profile = profile.update(fileName);

        Events.publish(new ProfileUpdatedEvent(originalProfile));
    }

    // 닉네임 변경
    public void updateNickname(String nickName) {
        this.nickname = this.nickname.update(nickName);
    }

    // 자기소개 변경
    public void updateIntroduce(String introduce) {
        this.introduce = this.introduce.update(introduce);
    }

    public static Member create(CreateMemberDomainModelDto createMemberDomainModelDto) {

        String nickname = RandomNicknameGenerateDomainService.generateNickname();
        return Member.builder()
                     .id(null)
                     .email(createMemberDomainModelDto.email())
                     .nickname(nickname)
                     .profile(RandomProfileGenerateDomainService.generate())
                     .social(createMemberDomainModelDto.social())
                     .introduce(null)
                     .deleted(false)
                     .build();
    }
}
