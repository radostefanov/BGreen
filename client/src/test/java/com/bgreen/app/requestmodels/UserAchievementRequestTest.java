package com.bgreen.app.requestmodels;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UserAchievementRequestTest {

    private static UserAchievementRequest request = new UserAchievementRequest();

    @Test
    public void getAchievementId() {
        request.setAchievementId(Long.valueOf(1));
        assertTrue(request.getAchievementId().equals(Long.valueOf(1)));
    }

    @Test
    public void geetAchievementName() {
        request.setAchievementName("achievement name");
        assertTrue(request.getAchievementName().equals("achievement name"));
    }

}
