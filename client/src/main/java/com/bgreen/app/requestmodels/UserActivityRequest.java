package com.bgreen.app.requestmodels;

import java.util.Objects;

public class UserActivityRequest {
    private Long activityId;
    private int degreesBefore = 0;
    private int degreesAfter = 0;
    private int distance = 0;
    private int veganCoefficient = 0;
    private int energySavedSolar = 0;
    private int areaInsulated = 0;
    private double massBought = 0;


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

    public int getEnergySavedSolar() {
        return energySavedSolar;
    }

    public void setEnergySavedSolar(int energySavedSolar) {
        this.energySavedSolar = energySavedSolar;
    }

    public int getAreaInsulated() {
        return areaInsulated;
    }

    public void setAreaInsulated(int areaInsulated) {
        this.areaInsulated = areaInsulated;
    }

    public double getMassBought() {
        return massBought;
    }

    public void setMassBought(double massBought) {
        this.massBought = massBought;
    }

    @Override
    public boolean equals(Object object) {

        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        UserActivityRequest that = (UserActivityRequest) object;
        return getDegreesBefore() == that.getDegreesBefore()
                && getDegreesAfter() == that.getDegreesAfter()
                && getDistance() == that.getDistance()
                && getVeganCoefficient() == that.getVeganCoefficient()
                && getEnergySavedSolar() == that.getEnergySavedSolar()
                && getAreaInsulated() == that.getAreaInsulated()
                && Double.compare(that.massBought, massBought) == 0
                && Objects.equals(activityId, that.activityId);
    }


}
