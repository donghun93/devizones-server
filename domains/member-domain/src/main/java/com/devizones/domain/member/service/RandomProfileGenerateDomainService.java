package com.devizones.domain.member.service;

import java.util.Random;

public class RandomProfileGenerateDomainService {
    private final static Random random = new Random();
    public static String generate() {
        int number = random.nextInt(3) + 1;

        return switch (number) {
            case 1 ->
                    "default-1.png";
            case 2 ->
                    "default-2.png";
            case 3 ->
                    "default-3.png";
            default -> null;
        };
    }
}
