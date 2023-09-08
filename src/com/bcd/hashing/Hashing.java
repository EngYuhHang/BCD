package com.bcd.hashing;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.IntStream;

public class Hashing {
    public static String hash(String str, String algorithm) {

        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            // md.update(getSalt());

            //digest input
            byte[] bytes = md.digest(str.getBytes("UTF-8"));
            StringBuilder ab = new StringBuilder();
            // append the stringbuilder
            IntStream.range(0, bytes.length)
                    .forEach(i -> ab.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                            .substring(1)));

            return ab.toString();

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {

            return null;
        }
    }

    // generate salt for hashing
    public static byte[] getSalt() {
        SecureRandom generator = new SecureRandom();
        byte[] randomBytes = new byte[128];
        // generate random salt
        generator.nextBytes(randomBytes);
        // return
        return randomBytes;
    }

}
