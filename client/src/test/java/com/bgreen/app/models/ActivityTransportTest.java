package com.bgreen.app.models;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ActivityTransportTest {

    private int id = 1;
    private String createdAt = "2000,01,01,00,00,00";
    private String updatedAt = "2001,01,01,00,00,00";
    private String name = "public transport instead of car";
    private int co2Points = 10;
    private int co2Interval = 0;
    private double distance = 20;
    private double distance2 = 25;

    private ActivityTransport activ = new ActivityTransport(id, createdAt, updatedAt, name, co2Points, co2Interval, distance);
    private ActivityTransport activ2 = new ActivityTransport(id, createdAt, updatedAt, name, co2Points, co2Interval, distance);
    private ActivityMeal meal = new ActivityMeal(id, createdAt, updatedAt, name, co2Points, co2Interval, 10);

    @Test
    public void getType() {
        assertEquals(activ.getType(), "Transport");
    }

    @Test
    public void getDistance() {
        double d = 20;
        double delta = 1e-10;
        assertEquals(activ.getDistance(), d, delta);
    }

    @Test
    public void equals() {
        assertEquals(activ, activ2);
    }

    @Test
    public void notEquals() {
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
        activ2.setType("banaan");
        assertFalse(activ.equals(activ2));
    }

    @Test
    public void notEqualsDifferentDistance() {
        assertFalse(activ.equals(new ActivityTransport(id, createdAt, updatedAt, name, co2Points, co2Interval, distance2)));
    }

    @Test
    public void notEqualsDistanceAndType(){
        ActivityTransport temp = new ActivityTransport(id, createdAt, updatedAt, name, co2Points, co2Interval, distance2);
        temp.setType("Something else");
        assertFalse(activ.equals(temp));
    }

    @Test
    public void hashCodeTest(){
        assertTrue(activ.equals(activ2));
        assertTrue(activ.hashCode() == activ2.hashCode());
    }
}