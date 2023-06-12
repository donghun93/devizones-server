package com.devizones.domain.post.model;

import com.devizones.commons.CodeEnum;
import com.devizones.domain.post.exception.PostErrorCode;
import com.devizones.domain.post.exception.PostException;
import lombok.Getter;

import static com.devizones.commons.StringUtils.hasText;

@Getter
public enum ImageFormat implements CodeEnum {
    PNG,
    JPG,
    JPEG,
    GIF;

    public static ImageFormat validateAndExtractFormat(String fileName) {

        if (!hasText(fileName)) {
            throw new PostException(PostErrorCode.IMAGE_EMPTY);
        }

        String extension = extractExtension(fileName);
        try {
            return ImageFormat.valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new PostException(PostErrorCode.IMAGE_NOT_SUPPORT);
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
