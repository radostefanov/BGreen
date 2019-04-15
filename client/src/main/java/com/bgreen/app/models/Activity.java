package com.bgreen.app.models;

import com.bgreen.app.enums.ActivityCategory;

public class Activity {
    private int id;
    private String createdAt;
    private String updatedAt;
    private String name;
    private int co2Points;
    private int co2Interval;
    private ActivityCategory category;

    public Activity() {}

    /**
     * model for an activity a user can log.
     * @param id id of activity
     * @param createdAt date activity was created
     * @param updatedAt date activity was updated
     * @param name of activity
     * @param co2Points amount of points for activity
     * @param co2Interval interval of activity
     */
    public Activity(int id, String createdAt, String updatedAt,
                    String name, int co2Points, int co2Interval) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.co2Points = co2Points;
        this.co2Interval = co2Interval;
    }

    public int getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }

    public int getCo2Points() {
        return co2Points;
    }

    public int getCo2Interval() {
        return co2Interval;
    }

    public ActivityCategory getCategory() {
        return category;
    }

    public void setCategory(ActivityCategory category) {
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCo2Points(int co2Points) {
        this.co2Points = co2Points;
    }

    public void setCo2Interval(int co2Interval) {
        this.co2Interval = co2Interval;
    }
}
