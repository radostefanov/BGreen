package com.bgreen.app.reqmodels;

import com.bgreen.app.enums.ActivityType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserActivityRequest {
    public ActivityType type;

    @JsonProperty("activityId")
    private Long activityId;

    private int degreesBefore = 0;
    private int degreesAfter = 0;

    private int distance = 0;

    private int veganCoefficient = 0;

    private double energySavedSolar = 0;

    private int areaInsulated = 0;

    private int massBought = 0;


    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public int getDegreesBefore() {
        return degreesBefore;
    }

    public void setDegreesBefore(int degreesBefore) {
        this.degreesBefore = degreesBefore;
    }

    public int getDegreesAfter() {
        return degreesAfter;
    }

    public void setDegreesAfter(int degreesAfter) {
        this.degreesAfter = degreesAfter;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getVeganCoefficient() {
        return veganCoefficient;
    }

    public void setVeganCoefficient(int veganCoefficient) {
        this.veganCoefficient = veganCoefficient;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public double getEnergySavedSolar() {
        return energySavedSolar;
    }

    public void setEnergySavedSolar(double energySavedSolar) {
        this.energySavedSolar = energySavedSolar;
    }

    public int getAreaInsulated() {
        return areaInsulated;
    }

    public void setAreaInsulated(int areaInsulated) {
        this.areaInsulated = areaInsulated;
    }

    public int getMassBought() {
        return massBought;
    }

    public void setMassBought(int massBought) {
        this.massBought = massBought;
    }
}
