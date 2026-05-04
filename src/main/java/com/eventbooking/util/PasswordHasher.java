package com.eventbooking.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * PBKDF2 password hashing for stored customer credentials (no external dependencies).
 */
public final class PasswordHasher {

    private static final int ITERATIONS = 120_000;
    private static final int KEY_BITS = 256;
    private static final int SALT_BYTES = 16;

    private static final SecureRandom RNG = new SecureRandom();

    private PasswordHasher() {
    }

    public static byte[] newSalt() {
        byte[] salt = new byte[SALT_BYTES];
        RNG.nextBytes(salt);
        return salt;
    }

    public static byte[] derive(char[] password, byte[] salt) throws GeneralSecurityException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_BITS);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        try {
            return skf.generateSecret(spec).getEncoded();
        } finally {
            spec.clearPassword();
        }
    }

    public static String toBase64(byte[] raw) {
        return Base64.getEncoder().encodeToString(raw);
    }

    public static byte[] fromBase64(String b64) {
        return Base64.getDecoder().decode(b64.getBytes(StandardCharsets.UTF_8));
    }
}
