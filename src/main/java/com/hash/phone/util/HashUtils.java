package com.hash.phone.util;

import com.hash.phone.env.Environment;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import java.security.MessageDigest;
import java.util.Base64;

public class HashUtils {

    private static MessageDigest getDigest(String alg) {
        try {
            switch (alg) {
                case "SHA-1":
                    return MessageDigest.getInstance("SHA-1");
                case "SHA-2":
                    return MessageDigest.getInstance("SHA-224");
                case "SHA-3":
                    return new SHA3.Digest224();
                default:
                    return MessageDigest.getInstance(alg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HashUtils() {}

    public static String hash(String phone) {
        String alg = Environment.get().getProperty("hash.algorithm", "SHA-3");
        String salt = Environment.get().getProperty("hash.salt", "");
        byte[] hash = getDigest(alg).digest((salt + phone).getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
