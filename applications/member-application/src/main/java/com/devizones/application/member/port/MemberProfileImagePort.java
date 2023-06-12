package com.devizones.application.member.port;

import java.io.InputStream;

public interface MemberProfileImagePort {
    void upload(String fileName, InputStream inputStream);
    void remove(String fileName);
    boolean existImage(String fileName);
}
