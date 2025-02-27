package org.travelagency.service.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String ALL_CHARS = LOWERCASE + UPPERCASE + DIGITS;
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generatePassword() {
        int length = 8 + RANDOM.nextInt(13);
        StringBuilder password = new StringBuilder();

        password.append(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length())));

        password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));

        for (int i = 2; i < length; i++) {
            password.append(ALL_CHARS.charAt(RANDOM.nextInt(ALL_CHARS.length())));
        }

        return shuffleString(password.toString());
    }

    private String shuffleString(String input) {
        char[] characters = input.toCharArray();

        for (int i = characters.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }

        return new String(characters);
    }
}