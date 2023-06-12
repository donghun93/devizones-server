package com.devizones.util;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.util.Random;

public class RandomStringFactory {
    private static final Random random = new Random();

    public static String generateRandomString(int minLength, int maxLength) {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .stringLengthRange(minLength, maxLength);
        return new EasyRandom(parameters).nextObject(String.class);
    }

    public static String generateRandomString(int minLength) {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .stringLengthRange(minLength, minLength);
        return new EasyRandom(parameters).nextObject(String.class);
    }
}