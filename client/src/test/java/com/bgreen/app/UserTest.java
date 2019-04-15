package com.bgreen.app;

import com.bgreen.app.models.User;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class UserTest {
    User user = new User("user", "pass");

    private double delta = 0.2;

    @Test
    public void getUsernameTest() {
        assertEquals(user.getUsername(), "user");
    }

    @Test
    public void getPasswordTest() {
        assertEquals(user.getPassword(), "pass");
    }

    @Test
    public void getEmailTest() {
        assertNull(user.getEmail());
    }

    @Test
    public void getPointsTest() {
        assertTrue(user.getPoints() == 0);
    }

    @Test
    public void setEmailTest() {
        user.setEmail("emailemail@email.com");
        assertEquals("emailemail@email.com", user.getEmail());
    }

    @Test
    public void setPointsTest() {
        user.setPoints(10000);
        assertTrue(user.getPoints() == 10000);
    }

    @Test
    public void setProfilePictureTest() {
        user.setProfilePicture("loser.png");
        assertEquals("loser.png", user.getProfilePicture());
    }

    @Test
    public void getProfilePictureTest() {
        assertNull(user.getProfilePicture());
    }

    @Test
    public void getDailyGoalTest() {
        user.setDailyGoal(20.2);
        assertEquals(20.2, user.getDailyGoal(), delta);
    }

    @Test
    public void getWeeklyGoalTest() {
        user.setWeeklyGoal(30.2);
        assertEquals(30.2, user.getWeeklyGoal(), delta);
    }

    @Test
    public void getMonthlyGoalTest() {
        user.setMonthlyGoal(40.2);
        assertEquals(40.2, user.getMonthlyGoal(), delta);
    }

    @Test
    public void testConstructor() {
        User user = new User("username", "password");
        assertNotNull(user);
    }

    @Test
    public void testConstructor2() {
        User user = new User("user", "pass", "email@email.com");
        assertNotNull(user);
    }

    @Test
    public void testConstructor3() {
        User user = new User("user", "pass", "email@email.com", 200);
        assertNotNull(user);
    }

    @Test
    public void testConstructor4() {
        User user = new User("username", "email", 20.4, 20.2, 30.2, 40.2);
        assertNotNull(user);
        assertEquals("email", user.getEmail());
    }
}
