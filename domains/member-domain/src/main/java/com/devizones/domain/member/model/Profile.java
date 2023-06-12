package com.devizones.domain.member.model;

import com.devizones.commons.StringUtils;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * 프로필 정보를 나타내는 클래스입니다.
 * 파일 이름과 해당 프로필의 포맷 정보를 가지고 있습니다.
 */
@Getter
public class Profile {

    private String fileName;
    private ProfileFormat profileFormat;

    /**
     * Profile 객체를 생성하는 정적 팩토리 메서드입니다.
     * 지정된 파일 이름으로부터 Profile 객체를 생성하고 반환합니다.
     *
     * @param fileName 파일 이름
     * @return 생성된 Profile 객체
     */
    @Builder
    private Profile(String fileName) {
        setFileName(fileName);
    }

    public Profile update(String fileName) {
        ProfileFormat profileFormat = ProfileFormat.validateAndExtractFormat(fileName);
        String randomFileName = UUID.randomUUID() + "." + profileFormat.name();
        return new Profile(randomFileName);
    }

    /**
     * 파일 이름과 해당 프로필의 포맷 정보를 설정합니다.
     * 유효한 파일 이름이 주어진 경우, 포맷 정보를 추출하여 설정합니다.
     * 파일 이름이 없는 경우, fileName과 profileFormat을 null로 설정합니다.
     *
     * @param fileName 파일 이름
     */
    private void setFileName(String fileName) {
        if (StringUtils.hasText(fileName)) {
            this.profileFormat = ProfileFormat.validateAndExtractFormat(fileName);
            this.fileName = fileName;
        } else {
            this.fileName = null;
            this.profileFormat = null;
        }
    }

    public static Profile create(String profile) {
        return new Profile(profile);
    }
}
