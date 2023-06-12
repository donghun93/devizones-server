package com.devizones.member.rds.adapter;

import com.devizones.domain.member.model.Member;
import com.devizones.member.rds.entity.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toDomain(MemberEntity memberEntity) {
        return Member.builder()
                     .id(memberEntity.getId())
                     .email(memberEntity.getEmail())
                     .nickname(memberEntity.getNickname())
                     .profile(memberEntity.getProfile())
                     .introduce(memberEntity.getIntroduce())
                     .deleted(memberEntity.isDeleted())
                     .social(memberEntity.getSocial().name())
                     .build();
    }

    public MemberEntity toEntity(Member member) {
        return MemberEntity.builder()
                           .id(member.getId())
                           .email(member.getEmail()
                                        .getValue())
                           .nickname(member.getNickname()
                                           .getValue())
                           .profile(member.getProfile()
                                          .getFileName())
                           .introduce(member.getIntroduce()
                                            .getValue())
                           .deleted(member.isDeleted())
                           .social(member.getSocial())
                           .build();
    }
}
