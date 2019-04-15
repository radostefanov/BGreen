package com.bgreen.app.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Home")
public class UserActivityHouse extends UserActivity {

    public int degreesBefore = 0;
    public int degreesAfter = 0;

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

    /**
     * Calculates the points.
     * @return said points.
     */
    public double calculatePoints() {

        double coefficient = (degreesBefore - degreesAfter) * 12.5;

        NumberFormat formatter = new DecimalFormat("#0.00");
        coefficient = Double.parseDouble((formatter.format(coefficient)).replace(",", "."));

        return coefficient;
    }
}
