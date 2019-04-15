package com.bgreen.app.models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * Achievement class to set properties for achievements.
 */
@Entity
@Table(name = "achievements")
public class Achievement extends AbstractModel {

    /**
     * Name of the achievement.
     */
    @NotBlank
    @Size(min = 3, max = 100)
    private String achievementName;

    /**
     * Requirements for the achievement.
     */
    private int requirements;

    /**
     * Find name of the achievement.
     *
     * @return name of achievement
     */
    public String getAchievementName() {
        return achievementName;
    }

    /**
     * Set name of achievement.
     *
     * @param achievementName New name of achievement.
     */
    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    /**
     * Find requirements for achievement.
     *
     * @return requirements for achievements
     */
    public int getRequirements() {
        return requirements;
    }

    /**
     * Set requirements for achievement.
     *
     * @param requirements New requirement.
     */
    public void setRequirements(int requirements) {
        this.requirements = requirements;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Achievement that = (Achievement) object;
        return requirements == that.requirements
                && Objects.equals(achievementName, that.achievementName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(achievementName, requirements);
    }
}
