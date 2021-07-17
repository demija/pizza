package ba.gabela.pizza.config.security;

public class SecurityConstants {
    public static final String ISSUER = "Referrizer";
    public static final long EXPIRATION_TIME = 3_600_000;
    public static final String EXPIRATION_LABEL = "expires_in";
    public static final String TOKEN_TYPE = "type";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String SIGN_UP_URL = "/register";
    public static final String MENU_URL = "/menu";
    public static final String ITEM_URL = "/items/**";
}
