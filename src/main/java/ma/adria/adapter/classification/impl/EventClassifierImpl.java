package ma.adria.adapter.classification.impl;

import ma.adria.adapter.classification.EventClassification;
import ma.adria.adapter.classification.EventClassifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static ma.adria.adapter.classification.EventClassification.*;
import static ma.adria.adapter.common.CoreConstant.EventLogsRows.EVENTNAME_KEY;
import static ma.adria.adapter.common.CoreConstant.EventLogsRows.URI_KEY;

/**
 * Component responsible for classifying events based on predefined rules.
 */
@Component
public class EventClassifierImpl implements EventClassifier {
    private final Map<String, EventClassification> eventNameToClassificationMap = new HashMap<>();
    private final Map<String, EventClassification> uriToClassificationMap = new HashMap<>();

    public EventClassifierImpl() {
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
        eventNameToClassificationMap.put("SIGNE_ADD_BENEFICIAIRE_V3.5", BENEFICIARY_MANAGEMENT);
        eventNameToClassificationMap.put("UPDATE_BENEFICIARY", BENEFICIARY_MANAGEMENT);
        eventNameToClassificationMap.put("UPDATE_GEST_CONTRAT", CHANGEMENT_INFO);
        eventNameToClassificationMap.put("UPDATE_ACTIVE_ABNN_V3.5", CHANGEMENT_INFO);
        eventNameToClassificationMap.put("UPDATE_ACTIVE_ABNN", CHANGEMENT_INFO);
        eventNameToClassificationMap.put("UPDATE_DEBLOC_ABN_V3.5", CHANGEMENT_INFO);
        eventNameToClassificationMap.put("MODIFICATION_UTILISATEUR_3.5", CHANGEMENT_INFO);
        eventNameToClassificationMap.put("MODIF_DEVISE", CHANGEMENT_INFO);

        // more rules as needed

        uriToClassificationMap.put("/clientafrica//virCompteCompteMultiDevise/signer", VIREMENT_COMPTE_A_COMPTE_MULTI_DEVISE);
        uriToClassificationMap.put("/clientafrica//virPermanentCAC/signer", VIREMENT_COMPTE_A_COMPTE_PERMANENT);
        uriToClassificationMap.put("/clientafrica/virBeneficiaireMultiDevise/signer", VIREMENT_VERS_BENEFICIARE_MULTI_DEVISE);
        uriToClassificationMap.put("/clientafrica/virPermanentMultiDevise/signer", VIREMENT_PERMANENT_MULTI_DEVISE);
        uriToClassificationMap.put("/clientafrica/virementMultiple/signer", VIREMENT_MULTIPLE);
        uriToClassificationMap.put("/banqueafrica/contratAbonnement/update", CHANGEMENT_INFO);
        uriToClassificationMap.put("/banqueafrica//abonne/update", CHANGEMENT_INFO);
        uriToClassificationMap.put("/banqueafrica//contratSMS/update", CHANGEMENT_INFO);

        // more rules as needed
    }

    @Override
    public EventClassification classify(final Map<String, Object> eventRow) {
        final String eventName = (String) eventRow.get(EVENTNAME_KEY);
        EventClassification classification;
        if ("UPDATE".equals(eventName) || "INSERT".equals(eventName)) {
            final String uri = (String) eventRow.get(URI_KEY);
            classification = uriToClassificationMap.getOrDefault(uri, NON_APPLICABLE);
        } else {
            classification = eventNameToClassificationMap.getOrDefault(eventName, NON_APPLICABLE);
        }
        return classification;
    }
}
