package com.devizones.domain.post.service;

import com.devizones.domain.post.model.ImageFormat;

import java.util.UUID;

public class RandomFileNameGenerateDomainService {

    public static String generate(String filePath) {
        ImageFormat imageFormat = ImageFormat.validateAndExtractFormat(filePath);
        return UUID.randomUUID() + "." + imageFormat.name();
    }
}
