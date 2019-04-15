package com.bgreen.app.models;

import org.junit.Test;

import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.*;

public class ActivityHomeTest {

    private int id = 1;
    private String createdAt = "2000,01,01,00,00,00";
    private String updatedAt = "2001,01,01,00,00,00";
    private String name = "lower temperature of home";
    private int co2Points = 10;
    private int co2Interval = 0;
    private double degreesBefore = 20;
    private double degreesAfter = 19;
    private HomeTempChange tempChange = new HomeTempChange(degreesBefore, degreesAfter);
    private HomeTempChange tempChange2 = new HomeTempChange(degreesBefore, 18);
    private final double delta = 1e-10;


    private ActivityHome activ = new ActivityHome(id, createdAt, updatedAt, name, co2Points, co2Interval, tempChange);
    private ActivityHome activ2 = new ActivityHome(id, createdAt, updatedAt, name, co2Points, co2Interval, tempChange);
    private ActivityMeal meal = new ActivityMeal(id, createdAt, updatedAt, name, co2Points, co2Interval, 10);

    @Test
    public void getType() {
        assertEquals(activ.getType(), "Home");
    }

    @Test
    public void getTempChange() {
        assertEquals(activ.getTempChange(), tempChange);
    }

    @Test
    public void equals() {
        assertEquals(activ, new ActivityHome(id, createdAt, updatedAt, name, co2Points, co2Interval, tempChange));
    }

    @Test
    public void notEquals() {
        double degrees = 0;
        assertFalse(activ.equals(new ActivityHome(id, createdAt, updatedAt, name, co2Points, co2Interval, new HomeTempChange(14, 15))));
    }

    @Test
    public void notEqualsSelf(){
        assertTrue(activ.equals(activ));
    }

    @Test
    public void notEqualsNull(){
        assertFalse(activ.equals(null));
    }

    @Test
    public void notEqualsDifferentType(){
        assertFalse(activ.equals(meal));
        assertFalse(activ.getType().equals(meal.getType()));
    }

    @Test
    public void notEqualsDifferentTempChange() {
        ActivityHome temp =  new ActivityHome(id, createdAt, updatedAt, name, co2Points, co2Interval, tempChange2);
        temp.setType("something else");
        assertFalse(activ.equals(temp));
    }

    @Test
    public void hashCodeTest(){
        assertTrue(activ.equals(activ2));
        assertTrue(activ.hashCode() == activ2.hashCode());
    }

}