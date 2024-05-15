package ma.adria.adapter.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class UserAgentUtils {

    public static String generateDeviceFingerprint(String username, String userAgent) {
        log.debug("Entering generateDeviceFingerprint with username: {} and userAgent: {}", username, userAgent);

        final String combinedInfo = username + userAgent;
        String fingerprint = HashUtils.hashWithSHA256(combinedInfo);

        log.debug("Generated device fingerprint: {}", fingerprint);
        log.debug("Exiting generateDeviceFingerprint");

        return fingerprint;
    }

    public void populateDeviceInfo(ObjectNode device, String userAgentString) {
        log.debug("Entering populateDeviceInfo with userAgentString: {}", userAgentString);

        if (userAgentString != null) {
            try {
                UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
                log.debug("Parsed UserAgent: {}", userAgent);

                // Extract device type based on operating system
                device.put("deviceType", userAgent.getOperatingSystem().getDeviceType().getName());
                log.debug("Extracted device type: {}", userAgent.getOperatingSystem().getDeviceType().getName());

                // Extract browser information
                Browser browser = userAgent.getBrowser();
                device.put("browser", browser.getName());
                device.put("browserVersion", userAgent.getBrowserVersion().getVersion());
                log.debug("Extracted browser: {}, version: {}", browser.getName(), userAgent.getBrowserVersion().getVersion());

                // Extract operating system information
                OperatingSystem os = userAgent.getOperatingSystem();
                device.put("os", os.getName());
                device.put("manufacturer", os.getManufacturer().getName());
                device.put("model", os.getDeviceType().getName());
                log.debug("Extracted OS: {}, manufacturer: {}, model: {}", os.getName(), os.getManufacturer().getName(), os.getDeviceType().getName());
            } catch (Exception e) {
                log.error("Error parsing user agent string: {}. Exception: {}", userAgentString, e.getMessage(), e);
            }
        } else {
            log.warn("UserAgentString is null or empty, skipping device info population.");
        }

        log.debug("Exiting populateDeviceInfo");
    }
}
