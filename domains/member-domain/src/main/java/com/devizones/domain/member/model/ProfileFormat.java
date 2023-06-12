package com.devizones.domain.member.model;

import com.devizones.commons.CodeEnum;
import com.devizones.commons.StringUtils;
import com.devizones.domain.member.exception.MemberErrorCode;
import com.devizones.domain.member.exception.MemberException;

public enum ProfileFormat implements CodeEnum {
    PNG,
    JPG,
    JPEG;

    public static ProfileFormat validateAndExtractFormat(String fileName) {
        validateEmptyFileName(fileName);
        String extension = extractExtension(fileName);
        try {
            return ProfileFormat.valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MemberException(MemberErrorCode.PROFILE_NOT_SUPPORT);
        }
    }

    private static void validateEmptyFileName(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            throw new MemberException(MemberErrorCode.PROFILE_EMPTY);
        }
    }

    private static String extractExtension(String fileName) {
        if (fileName == null) {
            return null;
        }

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }

        return null;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
