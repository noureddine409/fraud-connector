package ma.adria.adapter.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoreConstant {
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
    }
}
