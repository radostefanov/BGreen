package com.bgreen.app.models;

import com.bgreen.app.enums.ActivityCategory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActivityTest {
    private int id = 1;
    private String createdAt = "2000,01,01,00,00,00";
    private String updatedAt = "2001,01,01,00,00,00";
    private String name = "lower temperature of home";
    private int co2Points = 10;
    private int co2Interval = 0;
    private ActivityCategory category = ActivityCategory.Meal;

    Activity activity = new Activity(id, createdAt, updatedAt, name, co2Points, co2Interval);

    @Before
    public void Setup(){
        activity.setCategory(category);
    }

    @Test
    public void getIdTest(){
        assertTrue(activity.getId() == 1);
    }

    @Test
    public void setIdTest(){
        activity.setId(2);
        assertTrue(activity.getId() == 2);
    }

    @Test
    public void getCreatedAtTest(){
        assertTrue(activity.getCreatedAt() == createdAt);
    }

    @Test
    public void setCreatedAtTest(){
        activity.setCreatedAt(updatedAt);
        assertTrue(activity.getCreatedAt() == updatedAt);
    }

    @Test
    public void getUpdatedAtTest(){
        assertTrue(activity.getUpdatedAt() == updatedAt);
    }

    @Test
    public void setUpdatedAtTest(){
        activity.setUpdatedAt(createdAt);
        assertTrue(activity.getUpdatedAt() == createdAt);
    }

    @Test
    public void getNameTest(){
        assertTrue(activity.getName() == name);
    }

    @Test
    public void setNameTest(){
        activity.setName("banaan");
        assertTrue(activity.getName() == "banaan");
    }

    @Test
    public void getPointsTest(){
        assertTrue(activity.getCo2Points() == 10);
    }

    @Test
    public void setCo2PointsTest(){
        activity.setCo2Points(100);
        assertTrue(activity.getCo2Points() == 100);
    }

    @Test
    public void getIntervalTest(){
        assertTrue(activity.getCo2Interval() == 0);
    }

    @Test
    public void setCo2IntervalTest(){
        activity.setCo2Interval(101);
        assertTrue(activity.getCo2Interval() == 101);
    }

    @Test
    public void getCategoryTest(){
        assertTrue(activity.getCategory().equals(ActivityCategory.Meal));
    }

    @Test
    public void SetCategoryTest(){
        activity.setCategory(ActivityCategory.Home);
        assertTrue(activity.getCategory().equals(ActivityCategory.Home));
    }



}
