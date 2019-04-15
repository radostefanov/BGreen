package com.bgreen.app;

import com.bgreen.app.models.Activity;
import com.bgreen.app.models.UserActivity;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserActivityTest {

    private double delta = 0.2;

    private UserActivity activ = new UserActivity(10L, new Activity(), 20.2);

    @Test
    public void DefaultConstructorTest() {
        assertNotNull(new UserActivity());
    }

    @Test
    public void ConstructorTest() {

        assertNotNull(activ);

        assertEquals(activ.getId(), 10L, delta);

        assertNotNull(activ.getActivity());

        assertEquals(20.2, activ.getPoints(), delta);
    }

    @Test
    public void getCreatedAtTest() {
        activ.setCreatedAt("22-03-2019");

        assertEquals("22-03-2019", activ.getCreatedAt());
    }

    @Test
    public void getIdTest() {
        activ.setId(10L);
        assertEquals(10L, activ.getId(), delta);
    }

    @Test
    public void getActivityTest() {
        Activity activity = new Activity(10, "10-10", "10-10",
                "Name", 10, 10);
        activ.setActivity(activity);
        assertEquals(activ.getActivity().getId(), 10);
    }

    @Test
    public void getPointsTest() {
        assertEquals(20.2, activ.getPoints(), delta);
    }

    @Test
    public void setPointsTest() {
        activ.setPoints(24.2);
        assertEquals(24.2, activ.getPoints(), delta);
    }

    @Test
    public void getEnergySavedSolarTest() {
        activ.setEnergySavedSolar(200);
        assertEquals(200, activ.getEnergySavedSolar());
    }

    @Test
    public void getAreaInsulatedTest() {
        activ.setAreaInsulated(222);
        assertEquals(222, activ.getAreaInsulated());
    }

    @Test
    public void getDegreesBeforeTest() {
        activ.setDegreesBefore(10);
        assertEquals(activ.getDegreesBefore(), 10, delta);
    }

    @Test
    public void getDegreesAfterTest() {
        activ.setDegreesAfter(11);
        assertEquals(11, activ.getDegreesAfter());
    }

    @Test
    public void getDistanceTest() {
        activ.setDistance(40);
        assertEquals(40, activ.getDistance());
    }

    @Test
    public void getVeganCoefficient() {
        activ.setVeganCoefficient(33);
        assertEquals(33, activ.getVeganCoefficient());
    }

    @Test
    public void getLocalProduce() {
        activ.setLocalProduce(1);
        assertEquals(1, activ.getLocalProduce());
    }
}
