package ma.adria.adapter.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@UtilityClass
@Slf4j
public class EventMappingFunctions {


    public static String mapAuthentication(Map<String, Object> eventRow, EventClassification classification, ObjectMapper objectMapper) {
        if (eventRow == null || classification == null || objectMapper == null) {
            log.error("Invalid input parameters for mapAuthentication");
            throw new IllegalArgumentException("Invalid input parameters for mapAuthentication");
        }

        try {
            // Create the root event object
            ObjectNode event = objectMapper.createObjectNode();

            // Populate event properties
            event.put("id", getStringValue(eventRow, "id"));
            event.put("timestamp", getStringValue(eventRow, "datecreated"));
            event.put("motif", getStringValue(eventRow, "motif"));
            event.put("canal", getCanalValue(eventRow));
            event.put("activityTime", getStringValue(eventRow, "activitytime"));
            event.put("username", getStringValue(eventRow, "actor"));

            // Construct location object
            ObjectNode location = objectMapper.createObjectNode();
            location.put("ipAddress", getStringValue(eventRow, "ipaddress"));
            location.put("ipAddress2", getStringValue(eventRow, "ipaddress2"));
            event.set("location", location);

            // Construct contrat object
            ObjectNode contrat = objectMapper.createObjectNode();
            contrat.put("contratID", getStringValue(eventRow, "contrat_id"));
            event.set("contrat", contrat);

            // Construct device info
            ObjectNode device = objectMapper.createObjectNode();
            device.put("macAddress", getStringValue(eventRow, "mac_adress"));
            device.put("deviceId", getStringValue(eventRow, "ref1"));
            populateDeviceInfo(device, getStringValue(eventRow, "ref1"));
            event.set("device", device);

            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for authentication event: {}", e.getMessage());
            return null;
        }
    }

    private String getStringValue(Map<String, Object> eventRow, String key) {
        Object value = eventRow.get(key);
        return value != null ? value.toString() : null;
    }

    private String getCanalValue(Map<String, Object> eventRow) {
        String plateforme = getStringValue(eventRow, "plateforme");
        return (plateforme != null && plateforme.equalsIgnoreCase("web")) ? "WEB" : "MOBILE";
    }

    private void populateDeviceInfo(ObjectNode device, String userAgentString) {
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

    public static String mapVirementCompteACompte(Map<String, Object> eventRow, EventClassification classification, ObjectMapper objectMapper) {
        return mapEvent(eventRow, classification, objectMapper);
    }

    private static String mapEvent(Map<String, Object> eventRow, EventClassification classification, ObjectMapper objectMapper) {
        return null;
    }

    public static String mapVirementVersBeneficiaire(Map<String, Object> eventRow, EventClassification classification, ObjectMapper objectMapper) {
        return mapEvent(eventRow, classification, objectMapper);
    }

    public static String mapVirementPermanent(Map<String, Object> eventRow, EventClassification classification, ObjectMapper objectMapper) {
        return mapEvent(eventRow, classification, objectMapper);
    }

    public static String mapVirementMultiple(Map<String, Object> eventRow, EventClassification classification, ObjectMapper objectMapper) {
        return mapEvent(eventRow, classification, objectMapper);
    }

    public static String mapVirementCompteAComptePermanent(Map<String, Object> dataMap, EventClassification classification, ObjectMapper objectMapper) {
        return mapEvent(dataMap, classification, objectMapper);
    }

    public static String mapVirementCompteACompteMultiDevise(Map<String, Object> dataMap, EventClassification classification, ObjectMapper objectMapper) {
        return mapEvent(dataMap, classification, objectMapper);
    }

    public static String mapRemiseOrdre(Map<String, Object> dataMap, EventClassification classification, ObjectMapper objectMapper) {
        return mapEvent(dataMap, classification, objectMapper);
    }


}

