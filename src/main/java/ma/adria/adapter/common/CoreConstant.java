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
    public static class EventNames {
        public static final String AUTHENTICATION = "AUTHENTICATION";
        public static final String VIREMENT_COMPTE_A_COMPTE = "VIREMENT_COMPTE_A_COMPTE";
        public static final String VIREMENT_VERS_BENEFICIARE = "VIREMENT_VERS_BENEFICIARE";
        public static final String VIREMENT_PERMANENT = "VIREMENT_PERMANENT";
        public static final String VIREMENT_MULTIPLE = "VIREMENT_MULTIPLE";
        public static final String VIREMENT_COMPTE_A_COMPTE_PERMANENT = "VIREMENT_COMPTE_A_COMPTE_PERMANENT";
        public static final String VIREMENT_COMPTE_A_COMPTE_MULTI_DEVISE = "VIREMENT_COMPTE_A_COMPTE_MULTI_DEVISE";
        public static final String REMISE_ORDRE = "REMISE_ORDRE";
        public static final String VIREMENT_VERS_BENEFICIARE_MULTI_DEVISE = "VIREMENT_VERS_BENEFICIARE_MULTI_DEVISE";
        public static final String VIREMENT_PERMANENT_MULTI_DEVISE = "VIREMENT_PERMANENT_MULTI_DEVISE";
        public static final String CHANGE_INFO = "CHANGE_INFO";
        public static final String BENEFICIARY_MANAGEMENT = "BENEFICIARY_MANAGEMENT";
        public static final String DEMANDE_CHEQUIER = "DEMANDE_CHEQUIER";
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
