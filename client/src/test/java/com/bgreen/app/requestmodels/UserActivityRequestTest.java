package com.bgreen.app.requestmodels;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserActivityRequestTest {

    private static UserActivityRequest request = new UserActivityRequest();
    UserActivityRequest test = new UserActivityRequest();

    @Before
    public void init() {
        request.setActivityId(2147483649L);
        request.setDegreesBefore(20);
        request.setDistance(10);
        request.setDegreesAfter(19);
        request.setVeganCoefficient(5);
        request.setAreaInsulated(15);
        request.setEnergySavedSolar(40);
        request.setMassBought(1);

        test.setActivityId(2147483649L);
        test.setDegreesBefore(20);
        test.setDegreesAfter(19);
        test.setDistance(10);
        test.setVeganCoefficient(5);
        test.setEnergySavedSolar(40);
        test.setAreaInsulated(15);
        test.setMassBought(1);
    }

    @Test
    public void getActivityId() {
        assertTrue(request.getActivityId().equals(2147483649L));
    }

    @Test
    public void getDegreesBefore() {
        assertEquals(request.getDegreesBefore(), 20);
    }

    @Test
    public void getDegreesAfter() {
        assertEquals(request.getDegreesAfter(), 19);
    }

    @Test
    public void getDistance() {
        assertEquals(request.getDistance(), 10);
    }

    @Test
    public void getVeganCoefficient() {
        assertEquals(request.getVeganCoefficient(), 5);
    }

    @Test
    public void getEnergySavedSolar() {
        assertEquals(request.getEnergySavedSolar(), 40);
    }

    @Test
    public void getAreaInsulated() {
        assertEquals(request.getAreaInsulated(), 15);
    }

    @Test
    public void getLocalProduce() {
        assertEquals(0, Double.compare(1, request.getMassBought()));
    }

    @Test
    public void z_equalsSame() {
        assertEquals(test, test);
        assertTrue(test.equals(test));
    }

    @Test
    public void equalsOther() {
        test.setMassBought(1);
        assertEquals(request, test);
    }

    @Test
    public void a_equalsNull() {
        assertEquals(false, request.equals(null));
    }

    @Test
    public void equalsOtherObject() {
        assertFalse(test.equals(10));
    }

    @Test
    public void equalsFalseDegreesBefore() {
        test.setDegreesBefore(1);
        assertFalse(request.equals(test));
    }

    @Test
    public void equalsFalseDegreesAfter() {
        test.setDegreesBefore(request.getDegreesBefore());
        test.setDegreesAfter(1);
        assertFalse(request.equals(test));
    }

    @Test
    public void equalsFalseDistance() {
        test.setDegreesAfter(request.getDegreesAfter());
        test.setDistance(1);
        assertFalse(request.equals(test));
    }

    @Test
    public void equalsFalseVeganCoefficient() {
        test.setDistance(request.getDistance());
        test.setVeganCoefficient(1);
        assertFalse(request.equals(test));
    }

    @Test
    public void equalsFalseEnergySavedSolar() {
        test.setVeganCoefficient(request.getVeganCoefficient());
        test.setEnergySavedSolar(1);
        assertFalse(request.equals(test));
    }

    @Test
    public void equalsFalseAreaInsulated() {
        test.setEnergySavedSolar(request.getEnergySavedSolar());
        test.setAreaInsulated(1);
        assertFalse(request.equals(test));
    }

    @Test
    public void equalsFalseActivityId() {
        test.setAreaInsulated(request.getAreaInsulated());
        test.setActivityId(1L);
        assertFalse(request.equals(test));
    }

    @Test
    public void equalsFalseMass() {
        test.setAreaInsulated(request.getAreaInsulated());
        test.setMassBought(33);
        assertFalse(request.equals(test));
    }
}
