package com.bgreen.app.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SolarPanels")
public class UserActivitySolarPanels extends UserActivity {


    private double energySavedSolar = 0;

    public double getEnergySavedSolar() {

        return energySavedSolar;
    }

    public void setEnergySavedSolar(double energySavedSolar) {

        this.energySavedSolar = energySavedSolar;
    }

    /**
     * Calculates the points.
     * @return said points.
     */
    public double calculatePoints() {

        double coefficient = energySavedSolar * 24 * 365 * 0.6;

        NumberFormat formatter = new DecimalFormat("#0.00");
        coefficient = Double.parseDouble((formatter.format(coefficient)).replace(",", "."));

        return coefficient;
    }
}
