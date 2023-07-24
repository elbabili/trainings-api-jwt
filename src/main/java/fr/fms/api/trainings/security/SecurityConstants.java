package fr.fms.api.trainings.security;


public class SecurityConstants {
    public static final String HEADER_STRING = "Authorization";
    public static final String SECRET = "elbab@gmail.com";
    public static final long EXPIRATION_TIME = 10 * 60 * 1000;  //10 mn en ms = 10 * 60sec * 1000ms = 600000
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ERROR_MSG = "error-message";
}

