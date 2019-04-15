package com.bgreen.app.models;

import java.util.Objects;

public class ActivityTransport extends Activity {
    private String type = "Transport";
    private double distance;

    /**
     * model for an activity a user can log.
     * @param id id of activity
     * @param createdAt date activity was created
     * @param updatedAt date activity was updated
     * @param name of activity
     * @param co2Points amount of points for activity
     * @param co2Interval interval of activity
     * @param distance distance user travelled
     */
    public ActivityTransport(int id, String createdAt, String updatedAt,
                             String name, int co2Points, int co2Interval, double distance) {
        super(id, createdAt, updatedAt, name, co2Points, co2Interval);
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(String string) {
        this.type = string;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ActivityTransport that = (ActivityTransport) object;
        return Double.compare(that.getDistance(), getDistance()) == 0
                && getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getDistance());
    }
}
