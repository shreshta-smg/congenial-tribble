package tech.reactivemedia.billingsvc.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public final class HashUtil {
    private static final String SHA_1 = "^[0-9a-f]{40}$";

    public static boolean isValidSHA1(String s) {
        return s.matches(SHA_1);
    }

    public static String generateOrderId() {
        return generateIdRef();
    }

    public static String generatePaymentId() {
        return generateIdRef();
    }

    private static String generateIdRef() {
        var orderId = UUID.randomUUID().toString().replaceAll("-", "");
        var sha1Bytes = calculateSHA1(orderId).getBytes();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(sha1Bytes);
    }

    public static String calculateSHA1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));

            // Pad with leading zeros if necessary
            while (hashText.length() < 40) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
