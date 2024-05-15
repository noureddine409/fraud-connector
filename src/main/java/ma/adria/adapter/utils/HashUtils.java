package ma.adria.adapter.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@UtilityClass
@Slf4j
public class HashUtils {

    public static String hashWithSHA256(String input) {
        log.debug("Entering hashWithSHA256 with input: {}", input);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            log.debug("MessageDigest instance for SHA-256 created successfully.");

            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            log.debug("Hash bytes generated successfully.");

            // Convert byte array to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            String result = hexString.toString();
            log.debug("Generated SHA-256 hash: {}", result);

            log.debug("Exiting hashWithSHA256");
            return result;
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA-256 algorithm not available: ", e);
            return null;
        }
    }
}
