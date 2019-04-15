package com.bgreen.app.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class HomeTempChangeTest {

    private double degreesBefore = 20;
    private double degreesAfter = 19;
    private HomeTempChange changeOne = new HomeTempChange(degreesBefore, degreesAfter);
    private HomeTempChange changeTwo = new HomeTempChange(degreesAfter, degreesBefore);
    private double delta = 1e-10;


    @Test
    public void getDegreesBefore() {
        assertEquals(changeOne.getDegreesBefore(),20, delta);
    }

    @Test
    public void getDegreesAfter() {
        assertEquals(changeOne.getDegreesAfter(),19, delta);
    }

    @Test
    public void getTempChange() {
        assertEquals(changeOne.getTempChange(),-1, delta);
    }

    @Test
    public void getTempChangeNegative() {
        assertEquals(changeTwo.getTempChange(), 1, delta);
    }

    @Test
    public void equals() {
        assertEquals(changeOne, new HomeTempChange(20,19));
    }

    @Test
    public void notEquals() {
        assertFalse(changeOne.equals(changeTwo));
    }

    @Test
    public void notEqualsNull(){
        assertFalse(changeOne.equals(null));
    }

    @Test
    public void notEqualsOtherClass(){
        assertFalse(changeOne.equals(new ActivityMeal(1, "1a", "1a", "1a", 1, 1, 1)));
    }

    @Test
    public void notEqualsBefore(){
        assertFalse(changeOne.equals(new HomeTempChange(degreesAfter, degreesAfter)));
    }

    @Test
    public void notEqualsAfter(){
        assertFalse(changeOne.equals(new HomeTempChange(degreesBefore, degreesBefore)));
    }

    @Test
    public void notEqualsBothFields(){
        assertFalse(changeOne.equals(new HomeTempChange(1, 1)));
    }
}