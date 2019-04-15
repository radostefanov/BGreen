package com.bgreen.app.models;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("LocalProduction")
public class UserActivityLocalProduction extends UserActivity {

    private double massBought = 0;

    public double getMassBought() {
        return massBought;
    }

    public void setMassBought(double massBought) {
        this.massBought = massBought;
    }


    @Override
    public double calculatePoints() {
        double points;
        points = 1.4 * massBought;

        NumberFormat formatter = new DecimalFormat("#0.00");
        points = Double.parseDouble((formatter.format(points)).replace(",", "."));

        return points;
    }
}
