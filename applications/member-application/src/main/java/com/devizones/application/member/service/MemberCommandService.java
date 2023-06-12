package com.devizones.application.member.service;

import com.devizones.application.member.dto.*;
import com.devizones.application.member.exception.MemberServiceException;
import com.devizones.domain.member.dto.CreateMemberDomainModelDto;
import lombok.RequiredArgsConstructor;
import com.devizones.domain.member.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.devizones.application.member.port.MemberCommandPort;
import com.devizones.application.member.port.MemberLoadPort;
import com.devizones.application.member.port.MemberProfileImagePort;

import static com.devizones.application.member.exception.MemberServiceErrorCode.MEMBER_NICKNAME_DUPLICATE;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCommandService {

    private final MemberCommandPort memberCommandPort;
    private final MemberLoadPort memberLoadPort;
    private final MemberProfileImagePort memberProfileImagePort;

    // 회원 등록
    public MemberDto registerOrLogin(MemberCreateCommand command) {
        Member member = null;

        if(!memberLoadPort.existByEmail(command.email())) {
            while(true) {
                try {
                    CreateMemberDomainModelDto createMemberDomainModelDto = command.toDomainModelDto();
                    member = Member.create(createMemberDomainModelDto);
                    member = memberCommandPort.save(member);
                    break;
                } catch (DataIntegrityViolationException e) {
                    log.info("닉네임이 중복되었습니다.");
                }
            }
        } else {
            member = memberLoadPort.findByEmail(command.email());
        }

        return MemberDto.of(member);
    }

    // 닉네임 변경
    @Transactional
    public void updateNickname(Long memberId, MemberNicknameUpdateCommand command) {

        if(memberLoadPort.existByNickname(command.nickname())) {
            throw new MemberServiceException(MEMBER_NICKNAME_DUPLICATE);
        }

        Member member = memberLoadPort.findById(memberId);
        member.updateNickname(command.nickname());
        memberCommandPort.update(member);
    }

    // 자기소개 변경
    @Transactional
    public void updateIntroduce(Long memberId, MemberIntroduceUpdateCommand command) {
        Member member = memberLoadPort.findById(memberId);
        member.updateIntroduce(command.introduce());
        memberCommandPort.update(member);
    }

    // 프로필 변경
    @Transactional
    public void updateProfile(Long memberId, MemberProfileUpdateCommand command) {
        Member member = memberLoadPort.findById(memberId);
        member.updateProfile(command.fileName());
        memberCommandPort.update(member);

        memberProfileImagePort.upload(member.getProfile().getFileName(), command.inputStream());
    }
}
