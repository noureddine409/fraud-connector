package ma.adria.adapter.classification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import ma.adria.adapter.common.CoreConstant.Topics;
import ma.adria.adapter.mapper.EventMapper;
import ma.adria.adapter.mapper.EventMappingFunctions;

/**
 * Enum representing different classifications of events with associated mapping functions and topic IDs.
 */
@Getter
@AllArgsConstructor
public enum EventClassification {
    AUTHENTICATION(EventMappingFunctions::mapAuthentication, Topics.AUTHENTICATION),
    VIREMENT_COMPTE_A_COMPTE(EventMappingFunctions::mapVirementCompteACompte, Topics.VIREMENT_COMPTE_A_COMPTE),
    VIREMENT_VERS_BENEFICIARE(EventMappingFunctions::mapVirementVersBeneficiaire, Topics.VIREMENT_VERS_BENEFICIARE),
    VIREMENT_PERMANENT(EventMappingFunctions::mapVirementPermanent, Topics.VIREMENT_PERMANENT),
    VIREMENT_MULTIPLE(EventMappingFunctions::mapVirementMultiple, Topics.VIREMENT_MULTIPLE),
    VIREMENT_COMPTE_A_COMPTE_PERMANENT(EventMappingFunctions::mapVirementCompteAComptePermanent, Topics.VIREMENT_COMPTE_A_COMPTE_PERMANENT),
    VIREMENT_COMPTE_A_COMPTE_MULTI_DEVISE(EventMappingFunctions::mapVirementCompteACompteMultiDevise, Topics.VIREMENT_COMPTE_A_COMPTE_MULTI_DEVISE),
    REMISE_ORDRE(EventMappingFunctions::mapRemiseOrdre, Topics.REMISE_ORDRE),
    NON_APPLICABLE(null, null);

    private final EventMapper mapProcessingFunction; // Function to map event data to a string representation
    private final String topicId; // Identifier of the associated Kafka topic

}
