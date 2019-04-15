package com.bgreen.app;

import com.bgreen.app.auth.AuthUser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AuthUserTest {

    private double delta = 0.2;

    @Test
    public void getPointsTest() {
        AuthUser.setPoints(0);
        assertEquals(0, AuthUser.getPoints(), delta);
    }

    @Test
    public void setPoints() {
        AuthUser.setPoints(20.3);
        assertEquals(20.3, AuthUser.getPoints(), delta);
    }

    @Test
    public void setToken() {
        AuthUser.setToken("Argumentative");
        assertEquals("Argumentative", AuthUser.getToken());
    }
}
