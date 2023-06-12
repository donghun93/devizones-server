package com.devizones.domain.member.service;

import java.util.Random;

public class RandomNicknameGenerateDomainService {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    public static String generateNickname() {
        // 닉네임 길이 랜덤 생성 (2~10글자)
        int length = RANDOM.nextInt(9) + 2;

        StringBuilder nickname = new StringBuilder(length);

        // 닉네임 생성
        for (int i = 0; i < length; i++) {
            nickname.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return nickname.toString();
    }
}
