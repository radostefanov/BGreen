package com.bgreen.app.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * Goal class to set properties gor goals.
 */
@Entity
@Table(name = "goals")
public class Goal extends AbstractModel {

    /**
     * The ID of the user who set this goal.
     */
    private int userId;

    /**
     * Starting date of goal.
     */
    @NotBlank
    private String beginDate;

    /**
     * End date of goal.
     */
    @NotBlank
    private String endDate;

    /**
     * Find user id for this goal.
     * @return ID of the user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Change the id of the user.
     * @param userId id of the new user.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Get the starting date of this goal.
     * @return the start date
     */
    public String getBeginDate() {
        return beginDate;
    }

    /**
     * Set the start date of the goal.
     * @param beginDate date the goal started.
     */
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * Get the end date of the goal.
     * @return end date
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Set new end date for goal.
     * @param endDate date the goal ends.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

