package ma.adria.adapter.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ma.adria.adapter.classification.EventClassification;
import ma.adria.adapter.utils.UserAgentUtils;

import java.util.Map;
import java.util.Objects;

import static ma.adria.adapter.common.CoreConstant.EventLogsRows.*;

/**
 * Utility class containing functions to map event data to JSON objects for specific event types.
 */
@UtilityClass
@Slf4j
public class EventMappingFunctions {

    public static String mapAuthentication(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {
        assertParametersNotNull(eventRow, classification, objectMapper);
        try {
            ObjectNode event = mapSharedAttributes(eventRow, objectMapper);
            ObjectNode device = createDeviceNode(eventRow, objectMapper);
            event.set("device", device);
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for authentication event: {}", e.getMessage());
            return null;
        }
    }

    public static String mapVirementCompteACompte(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {
        assertParametersNotNull(eventRow, classification, objectMapper);
        try {
            ObjectNode event = mapSharedAttributes(eventRow, objectMapper);
            event.set("device", null); // No device information

            // Additional virement Compte a compte event specific fields
            mapVirementSharedAttributes(event);

            return objectMapper.writeValueAsString(event);

        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for Virement Compte a compte event: {}", e.getMessage());
            return null;
        }
    }


    public static String mapVirementVersBeneficiaire(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {
        assertParametersNotNull(eventRow, classification, objectMapper);
        try {
            ObjectNode event = mapSharedAttributes(eventRow, objectMapper);

            event.set("device", null); // No device information for remise ordre

            // Additional virement vers beneficiaire specific fields
            mapVirementSharedAttributes(event);

            return objectMapper.writeValueAsString(event);

        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for Virement vers beneficiare event: {}", e.getMessage());
            return null;
        }
    }

    private static void mapVirementSharedAttributes(final ObjectNode event) {
        event.set("creditor", null);
        event.set("debitingAccount", null);
        event.set("type", null);
        event.set("executionDateType", null);
        event.set("currency", null);
        event.set("entryDate", null);
        event.set("executionDate", null);
        event.set("executionFrequency", null);
    }

    public static String mapVirementPermanent(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {
        assertParametersNotNull(eventRow, classification, objectMapper);
        try {
            ObjectNode event = mapSharedAttributes(eventRow, objectMapper);

            event.set("device", null); // No device information for remise ordre
            mapVirementSharedAttributes(event);

            // Additional virement permanent specific fields

            // ....

            return objectMapper.writeValueAsString(event);

        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for Virement permanent event: {}", e.getMessage());
            return null;
        }
    }

    public static String mapVirementMultiple(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {
        assertParametersNotNull(eventRow, classification, objectMapper);
        try {
            ObjectNode event = mapSharedAttributes(eventRow, objectMapper);

            event.set("device", null); // No device information for remise ordre

            // Additional virement multiple specific fields

            mapVirementSharedAttributes(event);
            event.set("creditors", null);
            event.set("nombreOperations", null);

            // ....

            return objectMapper.writeValueAsString(event);

        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for Virement multiple event: {}", e.getMessage());
            return null;
        }
    }

    public static String mapVirementCompteAComptePermanent(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {
        assertParametersNotNull(eventRow, classification, objectMapper);
        try {
            ObjectNode event = mapSharedAttributes(eventRow, objectMapper);

            event.set("device", null); // No device information for remise ordre
            mapVirementSharedAttributes(event);

            // Additional remise ordre specific fields

            // ....

            return objectMapper.writeValueAsString(event);

        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for Virement Compte a compte permanent event: {}", e.getMessage());
            return null;
        }
    }

    public static String mapVirementCompteACompteMultiDevise(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {
        assertParametersNotNull(eventRow, classification, objectMapper);
        try {
            ObjectNode event = mapSharedAttributes(eventRow, objectMapper);

            event.set("device", null); // No device information for remise ordre
            mapVirementSharedAttributes(event);

            // Additional virement compte a compte multi devise specific fields

            // ....

            return objectMapper.writeValueAsString(event);

        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for Virement Compte a compte multi devise event: {}", e.getMessage());
            return null;
        }
    }

    public static String mapRemiseOrdre(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {
        assertParametersNotNull(eventRow, classification, objectMapper);

        try {
            ObjectNode event = mapSharedAttributes(eventRow, objectMapper);

            event.set("device", null); // No device information for remise ordre

            // Additional remise ordre specific fields
            event.set("reference", null);
            event.set("natureRemise", null);
            event.set("format", null);
            event.set("donneurOrdre", null);
            event.set("compteDebit", null);
            event.set("file", null);
            event.set("executionDate", null);
            event.set("status", null);

            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for Remise Ordre event: {}", e.getMessage());
            return null;
        }
    }


    private static ObjectNode mapSharedAttributes(final Map<String, Object> eventRow, final ObjectMapper objectMapper) {
        ObjectNode event = createEventNode(eventRow, objectMapper);
        ObjectNode location = createLocationNode(eventRow, objectMapper);
        event.set("location", location);

        ObjectNode contrat = createContratNode(eventRow, objectMapper);
        event.set("contrat", contrat);
        return event;
    }

    private static ObjectNode createEventNode(final Map<String, Object> eventRow, final ObjectMapper objectMapper) {
        ObjectNode event = objectMapper.createObjectNode();
        event.put("id", getStringValue(eventRow, ID_KEY));
        event.put("timestamp", getStringValue(eventRow, TIMESTAMP_KEY));
        event.put("motif", getStringValue(eventRow, MOTIF_KEY));
        event.put("canal", getCanalValue(eventRow));
        event.put("activityTime", getStringValue(eventRow, ACTIVITY_TIME_KEY));
        event.put("username", getStringValue(eventRow, ACTOR_KEY));
        event.put("bankCode", getStringValue(eventRow, BANK_CODE_KEY));
        event.put("countryCode", getStringValue(eventRow, COUNTRY_CODE_KEY));
        event.set("segment", null); // not provided in event logs table
        return event;
    }

    private static ObjectNode createLocationNode(final Map<String, Object> eventRow, final ObjectMapper objectMapper) {
        ObjectNode location = objectMapper.createObjectNode();
        location.put("ipAddress", getStringValue(eventRow, IP_ADDRESS_KEY));
        location.put("ipAddress2", getStringValue(eventRow, IP_ADDRESS_2_KEY));
        location.set("geoLocation", null); // not provided from audit db
        return location;
    }

    private static ObjectNode createContratNode(final Map<String, Object> eventRow, final ObjectMapper objectMapper) {
        ObjectNode contrat = objectMapper.createObjectNode();
        contrat.put("contratID", getStringValue(eventRow, CONTRAT_ID_KEY));

        return contrat;
    }

    private static ObjectNode createDeviceNode(final Map<String, Object> eventRow, final ObjectMapper objectMapper) {
        final String userAgent = getStringValue(eventRow, REF1_KEY);
        if (Objects.isNull(userAgent)) {
            return null;
        }
        ObjectNode device = objectMapper.createObjectNode();
        device.put("macAddress", getStringValue(eventRow, MAC_ADDRESS_KEY));
        final String deviceId = UserAgentUtils.generateDeviceFingerprint(getStringValue(eventRow, ACTOR_KEY), getStringValue(eventRow, REF1_KEY));
        device.put("deviceId", deviceId);
        UserAgentUtils.populateDeviceInfo(device, getStringValue(eventRow, REF1_KEY));
        return device;
    }


    private String getStringValue(final Map<String, Object> eventRow, final String key) {
        Object value = eventRow.get(key);
        return value != null ? value.toString() : null;
    }

    private String getCanalValue(final Map<String, Object> eventRow) {
        String plateforme = getStringValue(eventRow, PLATEFORME_KEY);
        return (plateforme != null && plateforme.equalsIgnoreCase("web")) ? "WEB" : "MOBILE";
    }

    private static void assertParametersNotNull(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {
        if (eventRow == null || classification == null || objectMapper == null) {
            throw new IllegalArgumentException("Invalid input parameters for event mapping");
        }
    }

    public static String mapVirementVersBeneficiaireMultiDevise(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {
        assertParametersNotNull(eventRow, classification, objectMapper);
        try {
            ObjectNode event = mapSharedAttributes(eventRow, objectMapper);

            event.set("device", null); // No device information for remise ordre
            mapVirementSharedAttributes(event);
            // Additional VirementVersBeneficiaireMultiDevise specific fields

            // ....

            return objectMapper.writeValueAsString(event);

        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for VirementVersBeneficiaireMultiDevise event: {}", e.getMessage());
            return null;
        }
    }

    public static String mapVirementPermanentMultiDevise(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {
        assertParametersNotNull(eventRow, classification, objectMapper);
        try {
            ObjectNode event = mapSharedAttributes(eventRow, objectMapper);

            event.set("device", null); // No device information for remise ordre
            mapVirementSharedAttributes(event);
            // Additional VirementPermanentMultiDevise specific fields

            // ....

            return objectMapper.writeValueAsString(event);

        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for VirementPermanentMultiDevise event: {}", e.getMessage());
            return null;
        }
    }

    public static String mapChangementInfo(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {

        assertParametersNotNull(eventRow, classification, objectMapper);
        try {
            ObjectNode event = mapSharedAttributes(eventRow, objectMapper);

            event.set("device", null); // No device information for remise ordre

            // Additional ChangementInfo event specific fields

            // ....

            return objectMapper.writeValueAsString(event);

        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for ChangementInfo event: {}", e.getMessage());
            return null;
        }

    }

    public static String mapBeneficiaryManagementEvent(final Map<String, Object> eventRow, final EventClassification classification, final ObjectMapper objectMapper) {
        assertParametersNotNull(eventRow, classification, objectMapper);
        try {
            ObjectNode event = mapSharedAttributes(eventRow, objectMapper);

            event.set("device", null); // No device information for remise ordre
            final String action;
            final String eventFromAuditDB = getStringValue(eventRow, EVENTNAME_KEY);
            if ("SIGNE_ADD_BENEFICIAIRE_V3.5".equals(eventFromAuditDB)) {
                action = "ADD";
            } else if ("UPDATE_BENEFICIARY".equals(eventFromAuditDB)) {
                action = "REMOVE";
            } else {
                action = "NON_APPLICABLE";
            }
            // Additional BeneficiaryManagement event specific fields
            event.put("action", action);
            event.set("beneficiaryInfo", null);
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON for BeneficiaryManagement event: {}", e.getMessage());
            return null;
        }
    }
}

