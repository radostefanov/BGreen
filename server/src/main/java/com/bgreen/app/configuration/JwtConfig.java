package com.bgreen.app.configuration;


public class JwtConfig {
    public static final String SECRET =
            "13C879BE3FE8E46D95DBEDAA55DCC006A2B9EE22557406C2132DAE85139FB8C1";
    public static final long EXPIRATION_TIME = 86400000; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
