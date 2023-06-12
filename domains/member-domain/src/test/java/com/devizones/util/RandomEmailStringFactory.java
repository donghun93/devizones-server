package com.devizones.util;

import net.datafaker.Faker;

public class RandomEmailStringFactory {
    private final static Faker faker = new Faker();

    public static String getRandomStringEmail() {
        // faker.siliconValley().email();
        return faker.internet()
                    .emailAddress();
    }

    public static String getRandomStringEmail(int minLength, int maxLength) {

        String account = RandomStringFactory.generateRandomString(minLength, maxLength);
        String domain = faker.internet().domainName();

        return account + "@" + domain;
    }

    public static String getRandomStringInvalidEmail() {
        return faker.internet().domainName();
    }
}
