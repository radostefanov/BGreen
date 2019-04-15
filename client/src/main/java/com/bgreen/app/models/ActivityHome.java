package com.bgreen.app.models;

import java.util.Objects;

public class ActivityHome extends Activity {
    private String type = "Home";
    private HomeTempChange tempChange;


    /**
     * model for an activity a user can log.
     * @param id id of activity
     * @param createdAt date activity was created
     * @param updatedAt date activity was updated
     * @param name of activity
     * @param co2Points amount of points for activity
     * @param co2Interval interval of activity
     * @param tempChange the change in home temperature
     */
    public ActivityHome(int id, String createdAt, String updatedAt, String name,
                        int co2Points, int co2Interval, HomeTempChange tempChange) {
        super(id, createdAt, updatedAt, name, co2Points, co2Interval);
        this.tempChange = tempChange;
    }

    public String getType() {
        return type;
    }

    public void setType(String string) {
        this.type = string;
    }

    public HomeTempChange getTempChange() {
        return tempChange;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ActivityHome that = (ActivityHome) object;
        return getType().equals(that.getType())
                && getTempChange().equals(that.getTempChange());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getTempChange());
    }
}
