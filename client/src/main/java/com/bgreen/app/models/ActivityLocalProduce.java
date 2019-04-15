package com.bgreen.app.models;

import java.util.Objects;

public class ActivityLocalProduce extends Activity {

    private String type = "Produce";
    private double massBought;

    /**
     * model for an activity a user can log.
     * @param id id of activity
     * @param createdAt date activity was created
     * @param updatedAt date activity was updated
     * @param name of activity
     * @param co2Points amount of points for activity
     * @param co2Interval interval of activity
     * @param massBought the mass of local produce bought
     */
    public ActivityLocalProduce(int id, String createdAt, String updatedAt, String name,
                        int co2Points, int co2Interval, double massBought) {
        super(id, createdAt, updatedAt, name, co2Points, co2Interval);
        this.massBought = massBought;
    }

    public double getMassBought() {
        return massBought;
    }

    public void setMassBought(double massBought) {
        this.massBought = massBought;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ActivityLocalProduce that = (ActivityLocalProduce) object;
        return getMassBought() == that.getMassBought()
                && getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getMassBought());
    }
}
