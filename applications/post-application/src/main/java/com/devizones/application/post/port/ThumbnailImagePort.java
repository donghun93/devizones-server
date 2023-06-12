package com.devizones.application.post.port;

import java.io.InputStream;

public interface ThumbnailImagePort {
    void upload(String fileName, InputStream inputStream);
}
