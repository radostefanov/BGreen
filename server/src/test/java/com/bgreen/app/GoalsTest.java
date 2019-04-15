package com.bgreen.app;

import com.bgreen.app.models.Goal;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static junit.framework.TestCase.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GoalsTest extends AbstractTest {


    Goal demoGoal;

    @Override
    @Before
    public void setUp() {

        super.setUp();

        demoGoal = new Goal();
        demoGoal.setBeginDate("BeginDate");
        demoGoal.setEndDate("EndDate");
        demoGoal.setUserId(1);

    }


    @Test
    public void a_gettersSettersTest(){

        assertEquals(demoGoal.getBeginDate(), "BeginDate");
        assertEquals(demoGoal.getEndDate(), "EndDate");
        assertEquals(demoGoal.getUserId(), 1);
    }
}
