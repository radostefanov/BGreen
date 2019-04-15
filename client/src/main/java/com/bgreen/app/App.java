package com.bgreen.app;

import com.bgreen.app.interfaces.LoginScreen;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Declared final in accordance with checkstyle UtilityClass practices.
 * Results in private constructor which can thus not be called.
 */
@EnableConfigurationProperties(ConfigProperties.class)
public final class App {

    public static final String baseUrl = "http://" + "80.112.170.84:8080" + "/";

    /**
     * Utility class uncallable default private constructor.
     */
    private App() {

    }

    /**
     * Runs via spring-boot:run.
     *
     * @param args string array
     */
    public static void main(final String[] args) throws Exception {
        LoginScreen.execute(args);
    }
}
