package nvb.dev.officemanagement.constant;

public class SecurityConstant {

    private SecurityConstant() {
    }

    public static final String CLERK_URL = "/api/v1/clerkManagement/**";
    public static final String MANAGER_URL = "/api/v1/managerManagement/**";
    public static final String OFFICE_URL = "/api/v1/officeManagement/**";
    public static final String DOC_URL = "/api/v1/docManagement/**";
    public static final String USER_URL = "/api/v1/userManagement/**";
    public static final String AUTH_URL = "/api/v1/auth/**";

    public static final int TOKEN_EXPIRATION_TIME = 1000 * 60 * 24;
    public static final int REFRESH_TOKEN_EXPIRATION_TIME = 604800000;

    public static final String BEARER = "Bearer ";
    public static final String SECRET_KEY = "3910ea4f4f83c856cb26a29b048cdec3fc6dc2bd034ea97c9583e8ce08fffb5f";

}
