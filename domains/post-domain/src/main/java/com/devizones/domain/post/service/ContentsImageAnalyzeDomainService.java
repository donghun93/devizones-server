package com.devizones.domain.post.service;

import com.devizones.domain.post.model.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ContentsImageAnalyzeDomainService {
    public static List<Image> getImages(String contents) {
        List<String> images = new ArrayList<>();

        if (contents != null && !contents.isEmpty()) {
            String pattern = "!\\[[^\\]]*\\]\\(([^)]+)\\)";
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(contents);

            while (matcher.find()) {
                String value = matcher.group(1);
                String fileName = extractFileName(value);
                images.add(fileName);
            }
        }

        return images.stream()
                     .map(i -> Image.create(i, false))
                     .collect(Collectors.toList());
    }

    private static String extractFileName(String url) {
        int lastSlashIndex = url.lastIndexOf("/");
        if (lastSlashIndex != -1) {
            return url.substring(lastSlashIndex + 1);
        }
        return url;
    }
}
