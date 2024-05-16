package ma.adria.adapter.classification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.adria.adapter.common.CoreConstant.EventNames;
import ma.adria.adapter.mapper.EventMapper;
import ma.adria.adapter.mapper.EventMappingFunctions;

/**
 * Enum representing different classifications of events with associated mapping functions and topic IDs.
 */
@Getter
@AllArgsConstructor
public enum EventClassification {
    AUTHENTICATION(EventNames.AUTHENTICATION, EventMappingFunctions::mapAuthentication),
    VIREMENT_COMPTE_A_COMPTE(EventNames.VIREMENT_COMPTE_A_COMPTE, EventMappingFunctions::mapVirementCompteACompte),
    VIREMENT_VERS_BENEFICIARE(EventNames.VIREMENT_VERS_BENEFICIARE, EventMappingFunctions::mapVirementVersBeneficiaire),
    VIREMENT_PERMANENT(EventNames.VIREMENT_PERMANENT, EventMappingFunctions::mapVirementPermanent),
    VIREMENT_MULTIPLE(EventNames.VIREMENT_MULTIPLE, EventMappingFunctions::mapVirementMultiple),
    VIREMENT_COMPTE_A_COMPTE_PERMANENT(EventNames.VIREMENT_COMPTE_A_COMPTE_PERMANENT, EventMappingFunctions::mapVirementCompteAComptePermanent),
    VIREMENT_COMPTE_A_COMPTE_MULTI_DEVISE(EventNames.VIREMENT_COMPTE_A_COMPTE_MULTI_DEVISE, EventMappingFunctions::mapVirementCompteACompteMultiDevise),
    VIREMENT_VERS_BENEFICIARE_MULTI_DEVISE(EventNames.VIREMENT_VERS_BENEFICIARE_MULTI_DEVISE, EventMappingFunctions::mapVirementVersBeneficiaireMultiDevise),
    VIREMENT_PERMANENT_MULTI_DEVISE(EventNames.VIREMENT_PERMANENT_MULTI_DEVISE, EventMappingFunctions::mapVirementPermanentMultiDevise),
    REMISE_ORDRE(EventNames.REMISE_ORDRE, EventMappingFunctions::mapRemiseOrdre),
    CHANGEMENT_INFO(EventNames.CHANGE_INFO, EventMappingFunctions::mapChangementInfo),
    BENEFICIARY_MANAGEMENT(EventNames.BENEFICIARY_MANAGEMENT, EventMappingFunctions::mapBeneficiaryManagementEvent),
    DEMANDE_CHEQUIER(EventNames.DEMANDE_CHEQUIER, EventMappingFunctions::mapDemandeChequierEvent),
    NON_APPLICABLE(null, null);


    private final String eventName; // Identifier for event name
    private final EventMapper mapProcessingFunction; // Function to map event data to a string representation

}
