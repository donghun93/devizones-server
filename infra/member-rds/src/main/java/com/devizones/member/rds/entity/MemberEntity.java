package com.devizones.member.rds.entity;


import com.devizones.domain.member.model.Social;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.devizones.domain.member.model.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "member",
        uniqueConstraints = @UniqueConstraint(
                name = "member_uk_email_nickname",
                columnNames = { "email", "nickname" }
        )
)
public class MemberEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String email;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column(nullable = false)
    private String profile;

    @Column(length = 100)
    private String introduce;

    private boolean deleted;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private Social social;

    @Builder
    private MemberEntity(Long id, String email, String nickname, String profile, String introduce, boolean deleted, Social social) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
        this.introduce = introduce;
        this.deleted = deleted;
        this.social = social;
    }

    public void update(Member member) {
        this.nickname = (member.getNickname() != null) ? member.getNickname().getValue() : this.nickname;
        this.introduce = (member.getIntroduce() != null) ? member.getIntroduce().getValue() : this.introduce;
        this.profile = (member.getProfile() != null) ? member.getProfile().getFileName() : this.profile;
    }
}
