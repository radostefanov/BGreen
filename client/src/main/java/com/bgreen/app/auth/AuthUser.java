package com.bgreen.app.auth;


public class AuthUser {

    private static String token;

    private static double points = 0;

    public static double getPoints() {
        return points;
    }

    public static void setPoints(double points) {
        AuthUser.points = points;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        AuthUser.token = token;
    }
}
