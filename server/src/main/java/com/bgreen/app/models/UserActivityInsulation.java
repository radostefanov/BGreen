package com.bgreen.app.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Insulation")
public class UserActivityInsulation extends UserActivity {

    private int areaInsulated = 0;

    public int getAreaInsulated() {
        return areaInsulated;
    }

    public void setAreaInsulated(int areaInsulated) {
        this.areaInsulated = areaInsulated;
    }

    /**
     * Calculates the points.
     * @return said points.
     */
    public double calculatePoints() {

        double coefficient = areaInsulated * 1.6;

        NumberFormat formatter = new DecimalFormat("#0.00");
        coefficient = Double.parseDouble((formatter.format(coefficient)).replace(",", "."));
        return coefficient;
    }
}
