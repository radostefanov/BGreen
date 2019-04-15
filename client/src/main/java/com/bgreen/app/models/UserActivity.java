package com.bgreen.app.models;



public class UserActivity {

    private Long id;

    private Activity activity;

    private double points;

    private String createdAt;
    private int degreesBefore = 0;
    private int degreesAfter = 0;
    private int distance = 0;
    private int veganCoefficient = 0;
    private int energySavedSolar = 0;
    private int areaInsulated = 0;
    private int localProduce = 0;

    public UserActivity() {

    }

    /**
     * constructor for a useractivity.
     * @param id id of the useractivity
     * @param activity activity to link to user
     * @param points amount of points
     */
    public UserActivity(Long id, Activity activity, double points) {
        this.id = id;
        this.activity = activity;
        this.points = points;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
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

    public int getLocalProduce() {
        return localProduce;
    }

    public void setLocalProduce(int localProduce) {
        this.localProduce = localProduce;
    }
}
