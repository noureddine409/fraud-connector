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
        final String combinedInfo = username + userAgent;
        return HashUtils.hashWithSHA256(combinedInfo);
    }

    public void populateDeviceInfo(ObjectNode device, String userAgentString) {
        if (userAgentString != null) {
            try {
                UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);

                // Extract device type based on operating system
                device.put("deviceType", userAgent.getOperatingSystem().getDeviceType().getName());

                // Extract browser information
                Browser browser = userAgent.getBrowser();
                device.put("browser", browser.getName());
                device.put("browserVersion", userAgent.getBrowserVersion().getVersion());

                // Extract operating system information
                OperatingSystem os = userAgent.getOperatingSystem();
                device.put("os", os.getName());
                device.put("manufacturer", os.getManufacturer().getName());
                device.put("model", os.getDeviceType().getName());
            } catch (Exception e) {
                log.error("Error parsing user agent string: {}", e.getMessage());
            }
        }
    }
}
