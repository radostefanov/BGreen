package com.bgreen.app.models;

import java.util.Objects;

public class ActivityMeal extends Activity {
    private String type = "Meal";
    private int veganCoefficient;

    /**
     * model for an activity a user can log.
     * @param id id of activity
     * @param createdAt date activity was created
     * @param updatedAt date activity was updated
     * @param name of activity
     * @param co2Points amount of points for activity
     * @param co2Interval interval of activity
     * @param veganCoefficient veganCoefficicient
     */
    public ActivityMeal(int id, String createdAt, String updatedAt, String name, int co2Points,
                        int co2Interval, int veganCoefficient) {
        super(id, createdAt, updatedAt, name, co2Points, co2Interval);
        this.veganCoefficient = veganCoefficient;
    }

    public int getVeganCoefficient() {
        return veganCoefficient;
    }

    public String getType() {
        return type;
    }

    public void setType(String string) {
        this.type = string;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ActivityMeal that = (ActivityMeal) object;
        return getVeganCoefficient() == that.getVeganCoefficient()
                && getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getVeganCoefficient());
    }
}
