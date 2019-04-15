package com.bgreen.app.reqmodels;

public class UserAchievementRequest {

    private Long achievementId;

    private String achievementName;

    public Long getAchievementId() {
        return achievementId;
    }

    public void setActhievementId(Long achievementId) {
        this.achievementId = achievementId;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

}
