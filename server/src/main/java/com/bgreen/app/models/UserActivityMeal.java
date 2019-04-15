package com.bgreen.app.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Meal")
public class UserActivityMeal extends UserActivity {

    public int veganCoefficient = 0;

    public int getVeganCoefficient() {
        return veganCoefficient;
    }

    public void setVeganCoefficient(int veganCoefficient) {
        this.veganCoefficient = veganCoefficient;
    }

    /**
     * Calculates the points.
     * @return said points.
     */
    public double calculatePoints() {

        double coefficient = veganCoefficient * 0.30;

        NumberFormat formatter = new DecimalFormat("#0.00");
        coefficient = Double.parseDouble((formatter.format(coefficient)).replace(",", "."));

        return coefficient;
    }
}
