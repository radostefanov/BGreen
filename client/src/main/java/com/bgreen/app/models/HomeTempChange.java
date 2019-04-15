package com.bgreen.app.models;

import java.util.Objects;

public class HomeTempChange {

    private double degreesBefore;
    private double degreesAfter;

    public HomeTempChange(double degreesBefore, double degreesAfter) {
        this.degreesBefore = degreesBefore;
        this.degreesAfter = degreesAfter;
    }

    public double getDegreesBefore() {
        return degreesBefore;
    }

    public double getDegreesAfter() {
        return degreesAfter;
    }

    public double getTempChange() {
        return degreesAfter - degreesBefore;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        HomeTempChange that = (HomeTempChange) object;
        return Double.compare(that.getDegreesBefore(), getDegreesBefore()) == 0
                && Double.compare(that.getDegreesAfter(), getDegreesAfter()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDegreesBefore(), getDegreesAfter());
    }
}
