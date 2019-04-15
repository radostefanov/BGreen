package com.bgreen.app.models;

public class Achievement {

    private Long id;
    private String achievementName;
    private double requirements;


    /**
     * model for an achievement.
     * @param id id of achievement
     * @param achievementName name of achievement
     * @param requirements requirements of achievement
     */
    public Achievement(final Long id, final String achievementName, final double requirements) {
        this.id = id;
        this.achievementName = achievementName;
        this.requirements = requirements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAchievementName() {
        return this.achievementName;
    }

    public double getRequirements() {
        return this.requirements;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public void setRequirements(Double requirements) {
        this.requirements = requirements;
    }
}
