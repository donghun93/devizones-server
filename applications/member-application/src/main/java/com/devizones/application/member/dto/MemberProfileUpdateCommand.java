package com.devizones.application.member.dto;

import java.io.InputStream;

public record MemberProfileUpdateCommand(
        String fileName,
        InputStream inputStream
) {
}
