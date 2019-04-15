package com.bgreen.app.models;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AchievementTest {

    private Long id = Long.valueOf(1);
    private String achievementName = "test achievement";
    private double requirements = 10.0;

    Achievement testAchievement = new Achievement(id, achievementName, requirements);

    @Test
    public void getIdTest() {
        assertTrue(testAchievement.getId() == 1);
    }

    @Test
    public void setIdTest() {

        testAchievement.setId(Long.valueOf(2));
        assertTrue(testAchievement.getId() == 2);

    }

    @Test
    public void getAchievementNameTest() {

        assertTrue(testAchievement.getAchievementName() == "test achievement");

    }

    @Test
    public void setAchievementNameTest() {

        testAchievement.setAchievementName("test achievement 2");

        assertTrue(testAchievement.getAchievementName() == "test achievement 2");

    }

    @Test
    public void getRequirementsTest() {

        assertTrue(testAchievement.getRequirements() == 10);

    }

    @Test
    public void setRequirementsTest() {

        testAchievement.setRequirements(20.0);

        assertTrue(testAchievement.getRequirements() == 20.0);

    }
}
