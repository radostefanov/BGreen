package com.bgreen.app.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Travel")
public class UserActivityTravel extends UserActivity {

    private int distance = 0;

    public int getDistance() {

        return distance;
    }

    public void setDistance(int distance) {

        this.distance = distance;
    }

    /**
     * Calculates the points.
     * @return said points.
     */
    public double calculatePoints() {
        double coefficient;
        if (this.getActivity().getId().equals(Long.valueOf(2))) {
            coefficient = distance * 1.9;

            NumberFormat formatter = new DecimalFormat("#0.00");
            coefficient = Double.parseDouble((formatter.format(coefficient)).replace(",", "."));
            return coefficient;
        } else {
            coefficient = distance * 1.1;

            NumberFormat formatter = new DecimalFormat("#0.00");
            coefficient = Double.parseDouble((formatter.format(coefficient)).replace(",", "."));
            return coefficient;
        }
    }
}
