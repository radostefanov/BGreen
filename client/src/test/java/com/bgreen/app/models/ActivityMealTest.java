package com.bgreen.app.models;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ActivityMealTest {

    private int id = 1;
    private String createdAt = "2000,01,01,00,00,00";
    private String updatedAt = "2001,01,01,00,00,00";
    private String name = "vegetarian meal";
    private int co2Points = 10;
    private int co2Interval = 0;
    private int veganCoefficient = 20;
    private HomeTempChange tempChange = new HomeTempChange(10, 9);

    private ActivityMeal activ = new ActivityMeal(id, createdAt, updatedAt, name, co2Points, co2Interval, veganCoefficient);
    private ActivityMeal activ2 = new ActivityMeal(id, createdAt, updatedAt, name, co2Points, co2Interval, veganCoefficient);
    private ActivityHome home = new ActivityHome(10, "1a", "1a", name, co2Points, co2Interval, tempChange);
    private ActivityMeal activ3 = new ActivityMeal(id, createdAt, updatedAt, name, co2Points, co2Interval, 22);

    @Test
    public void getVeganCoefficient() {
        assertEquals(activ.getVeganCoefficient(), 20);
    }

    @Test
    public void getType() {
        assertEquals(activ.getType(), "Meal");
    }

    @Test
    public void equals() {
        assertEquals(activ, new ActivityMeal(id, createdAt, updatedAt, name, co2Points, co2Interval, veganCoefficient));
    }

    @Test
    public void notEquals() {
        assertFalse(activ.equals(new ActivityMeal(id, createdAt, updatedAt, name, co2Points, co2Interval, 19)));
    }

    @Test
    public void getId() {
        assertTrue(activ.getId() == 1);
    }

    @Test
    public void getCreatedAt() {
        assertEquals(activ.getCreatedAt(), "2000,01,01,00,00,00");
    }

    @Test
    public void getUpdatedAt() {
        assertEquals(activ.getUpdatedAt(), "2001,01,01,00,00,00");
    }

    @Test
    public void getName() {
        assertEquals(activ.getName(), "vegetarian meal");
    }

    @Test
    public void getCo2Points() {
        assertEquals(activ.getCo2Points(), 10);
    }

    @Test
    public void getCo2Interval() {
        assertEquals(activ.getCo2Interval(), 0);
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
    public void notEqualsDifferentClass(){
        assertFalse(activ.equals(home));
    }

    @Test
    public void notEqualsDifferentVeganCo(){
        assertFalse(activ3.equals(activ));
        assertFalse(activ.equals(activ3));
    }

    @Test
    public void notEqualsDifferentType(){
        activ2.setType("Banaan");
        assertFalse(activ.equals(activ2));
    }

    @Test
    public void notEqualsTypeAndVeganCo(){
       activ3.setType("banaan");
       assertFalse(activ.equals(activ3));
    }

    @Test
    public void hashCodeTest(){
        assertTrue(activ.equals(activ2));
        assertTrue(activ.hashCode() == activ2.hashCode());
    }

}