package ma.adria.adapter.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoreConstant {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DateTimeFormats {
        public static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss,SSSSSSSSS");
        public static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Topics {
        public static final String AUTHENTICATION = "auth";
        public static final String VIREMENT_COMPTE_A_COMPTE = "virCAC";
        public static final String VIREMENT_VERS_BENEFICIARE = "virVersBenef";
        public static final String VIREMENT_PERMANENT = "virPerm";
        public static final String VIREMENT_MULTIPLE = "virMult";
        public static final String VIREMENT_COMPTE_A_COMPTE_PERMANENT = "virCACPerm";
        public static final String VIREMENT_COMPTE_A_COMPTE_MULTI_DEVISE = "virCACMultiDevise";
        public static final String REMISE_ORDRE = "remise-ordre";
        public static final String VIREMENT_VERS_BENEFICIARE_MULTI_DEVISE = "virVersBenefMultiDevise";
        public static final String VIREMENT_PERMANENT_MULTI_DEVISE = "virPermMultiDevise";
        public static final String CHANGE_INFO = "changeInfo";
        public static final String BENEFICIARY_MANAGEMENT = "beneficiaryManagement";
        public static final String DEMANDE_CHEQUIER = "demandeCheq";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class EventLogsRows {
        public static final String ID_KEY = "id";
        public static final String TIMESTAMP_KEY = "datecreated";
        public static final String MOTIF_KEY = "motif";
        public static final String ACTIVITY_TIME_KEY = "activitytime";
        public static final String EVENTNAME_KEY = "eventname";
        public static final String URI_KEY = "uri";
        public static final String ACTOR_KEY = "actor";
        public static final String IP_ADDRESS_KEY = "ipaddress";
        public static final String IP_ADDRESS_2_KEY = "ipaddress2";
        public static final String BANK_CODE_KEY = "codebanqueassocie";
        public static final String COUNTRY_CODE_KEY = "codepaysassocie";
        public static final String CONTRAT_ID_KEY = "contrat_id";
        public static final String PLATEFORME_KEY = "plateforme";
        public static final String MAC_ADDRESS_KEY = "mac_address";
        public static final String REF1_KEY = "ref1";
    }
}
