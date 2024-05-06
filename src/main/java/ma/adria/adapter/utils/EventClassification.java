package ma.adria.adapter.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.Map;

@Getter
public enum EventClassification {
    AUTHENTICATION(EventMappingFunctions::mapAuthentication),
    VIREMENT_COMPTE_A_COMPTE(EventMappingFunctions::mapVirementCompteACompte),
    VIREMENT_VERS_BENEFICIARE(EventMappingFunctions::mapVirementVersBeneficiaire),
    VIREMENT_PERMANENT(EventMappingFunctions::mapVirementPermanent),
    VIREMENT_MULTIPLE(EventMappingFunctions::mapVirementMultiple),
    VIREMENT_COMPTE_A_COMPTE_PERMANENT(EventMappingFunctions::mapVirementCompteAComptePermanent),
    VIREMENT_COMPTE_A_COMPTE_MULTI_DEVISE(EventMappingFunctions::mapVirementCompteACompteMultiDevise),
    REMISE_ORDRE(EventMappingFunctions::mapRemiseOrdre),
    NON_APPLICABLE(null);

    private final TriFunction<Map<String, Object>, EventClassification, ObjectMapper, String> mapProcessingFunction;

    EventClassification(TriFunction<Map<String, Object>, EventClassification, ObjectMapper, String> mapProcessingFunction) {
        this.mapProcessingFunction = mapProcessingFunction;
    }
}
