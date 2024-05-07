package ma.adria.adapter.classification;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static ma.adria.adapter.classification.EventClassification.*;

/**
 * Component responsible for classifying events based on predefined rules.
 */
@Component
public class EventClassifier {
    private final Map<String, EventClassification> eventNameToClassificationMap = new HashMap<>();
    private final Map<String, EventClassification> uriToClassificationMap = new HashMap<>();

    public EventClassifier() {
        loadClassificationRules();
    }

    private void loadClassificationRules() {
        eventNameToClassificationMap.put("ConnexionOK", AUTHENTICATION);
        eventNameToClassificationMap.put("SIGNE_VIR_CPTACPT", VIREMENT_COMPTE_A_COMPTE);
        eventNameToClassificationMap.put("SIGNE_VIR_BEN", VIREMENT_VERS_BENEFICIARE);
        eventNameToClassificationMap.put("SIGNE_VIR_PERM", VIREMENT_PERMANENT);
        eventNameToClassificationMap.put("SIGNE_VIR_MULT", VIREMENT_MULTIPLE);
        eventNameToClassificationMap.put("SIGNE_VIR_PERM_CAC", VIREMENT_COMPTE_A_COMPTE_PERMANENT);
        eventNameToClassificationMap.put("SIGNE_RO", REMISE_ORDRE);
        // more rules as needed

        uriToClassificationMap.put("/clientafrica//virCompteCompteMultiDevise/signer", VIREMENT_COMPTE_A_COMPTE_MULTI_DEVISE);
        // more rules as needed
    }

    public EventClassification classify(Map<String, Object> eventRow) {
        final String eventName = (String) eventRow.get("eventname");
        EventClassification classification;
        if ("UPDATE".equals(eventName) || "INSERT".equals(eventName)) {
            final String uri = (String) eventRow.get("uri");
            classification = uriToClassificationMap.getOrDefault(uri, NON_APPLICABLE);
        } else {
            classification = eventNameToClassificationMap.getOrDefault(eventName, NON_APPLICABLE);
        }
        return classification;
    }
}
