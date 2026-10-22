package com.devforge.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Generates passwords using a cryptographically strong random source. */
public final class PasswordService {
    public static final int MIN_LENGTH = 4;
    public static final int MAX_LENGTH = 128;
    public static final int MAX_BATCH_SIZE = 20;

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{};:,.?/\\";
    private static final String AMBIGUOUS = "Il1O0o";
    private final SecureRandom random;

    public PasswordService() {
        this(new SecureRandom());
    }

    PasswordService(SecureRandom random) {
        this.random = random;
    }

    public String generate(int length, boolean upper, boolean lower, boolean digits,
                           boolean symbols, boolean excludeAmbiguous) {
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw new IllegalArgumentException("Choose a length from 4 to 128 characters.");
        }

        List<String> categories = new ArrayList<>(4);
        if (upper) categories.add(UPPER);
        if (lower) categories.add(LOWER);
        if (digits) categories.add(DIGITS);
        if (symbols) categories.add(SYMBOLS);
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("Select at least one character type.");
        }
        if (excludeAmbiguous) {
            categories.replaceAll(chars -> chars.chars()
                    .filter(ch -> AMBIGUOUS.indexOf(ch) < 0)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString());
        }
        categories.removeIf(String::isEmpty);
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("The selected character types have no usable characters.");
        }
        if (length < categories.size()) {
            throw new IllegalArgumentException("Password length must allow at least one character from every selected type.");
        }

        String alphabet = String.join("", categories);
        char[] result = new char[length];
        for (int i = 0; i < categories.size(); i++) {
            String category = categories.get(i);
            result[i] = category.charAt(random.nextInt(category.length()));
        }
        for (int i = categories.size(); i < result.length; i++) {
            result[i] = alphabet.charAt(random.nextInt(alphabet.length()));
        }
        for (int i = result.length - 1; i > 0; i--) {
            int swap = random.nextInt(i + 1);
            char current = result[i];
            result[i] = result[swap];
            result[swap] = current;
        }
        return new String(result);
    }

    public List<String> generateMultiple(int count, int length, boolean upper, boolean lower,
                                         boolean digits, boolean symbols, boolean excludeAmbiguous) {
        if (count < 1 || count > MAX_BATCH_SIZE) {
            throw new IllegalArgumentException("Choose between 1 and 20 passwords at a time.");
        }
        List<String> passwords = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            passwords.add(generate(length, upper, lower, digits, symbols, excludeAmbiguous));
        }
        return Collections.unmodifiableList(passwords);
    }

    /** A rough strength estimate based on the observed alphabet, length and repeated characters. */
    public String estimate(String password) {
        if (password == null || password.isEmpty()) return "—";
        int alphabetSize = 0;
        if (password.chars().anyMatch(Character::isUpperCase)) alphabetSize += 26;
        if (password.chars().anyMatch(Character::isLowerCase)) alphabetSize += 26;
        if (password.chars().anyMatch(Character::isDigit)) alphabetSize += 10;
        if (password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch))) alphabetSize += 32;

        long distinct = password.chars().distinct().count();
        double uniqueness = (double) distinct / password.length();
        double estimatedBits = password.length() * (Math.log(Math.max(1, alphabetSize)) / Math.log(2)) * uniqueness;
        if (estimatedBits < 35) return "Weak";
        if (estimatedBits < 60) return "Moderate";
        if (estimatedBits < 90) return "Strong";
        return "Very strong";
    }
}
