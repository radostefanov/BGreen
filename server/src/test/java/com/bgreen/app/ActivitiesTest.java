package com.bgreen.app;

import com.bgreen.app.enums.ActivityCategory;
import com.bgreen.app.enums.ActivityType;
import com.bgreen.app.enums.HttpMethod;
import com.bgreen.app.models.Activity;
import com.bgreen.app.models.User;
import com.bgreen.app.models.UserActivity;
import com.bgreen.app.models.UserActivityHouse;
import com.bgreen.app.repositories.ActivityRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@WithMockUser(username = "demoUser")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ActivitiesTest extends AbstractTest {


    Activity standardActivity;
    UserActivity userActivity;

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    @Before
    public void setUp() {

        super.setUp();

        standardActivity = new Activity();
        standardActivity.setName("Activity 1");
        standardActivity.setCo2Points(50);
        standardActivity.setCo2Interval(90);
        standardActivity.setCategory(ActivityCategory.Home);

        userActivity = new UserActivityHouse();
    }


    @Test
    @Transactional
    public void __clearDatabase(){

        activityRepository.truncate();
    }


    @Test
    @Transactional
    public void a_getActivitiesEmptyTest() throws Exception {


        Activity[] result = this.httpRequest("/api/activities/", HttpMethod.GET, null, Activity[].class, 200);

        assertEquals(result.length, 0);

    }

    @Test
    public void b_createNewActivity() throws Exception {



        Activity result = this.httpRequest("/api/activities/", HttpMethod.POST, standardActivity, Activity.class, 200);

        assertEquals(result, standardActivity);

    }

    @Test
    public void c_getActivities1ElementTest() throws Exception {

        Activity[] result = this.httpRequest("/api/activities/", HttpMethod.GET, null, Activity[].class, 200);

        assertEquals(1, result.length);

        assertEquals(result[0], standardActivity);

    }

    @Test
    public void d_updateActivity() throws Exception {

        standardActivity.setName("Activity 1 changed");
        standardActivity.setCo2Points(90);
        standardActivity.setCo2Interval(20);


        Activity result = this.httpRequest("/api/activities/1", HttpMethod.PUT, standardActivity, Activity.class, 200);

        assertEquals(result, standardActivity);

    }


    @Test
    public void e_getSpecificUpdatedActivity() throws Exception {

        standardActivity.setName("Activity 1 changed");
        standardActivity.setCo2Points(90);
        standardActivity.setCo2Interval(20);

        Activity result = this.httpRequest("/api/activities/1", HttpMethod.GET, null, Activity.class, 200);

        assertEquals(result, standardActivity);

    }


    @Test
    public void f_deleteExistingActivity() throws Exception {


         this.httpRequest("/api/activities/1", HttpMethod.DELETE, null, null, 200);


    }

    @Test
    public void g_deleteNonExistingActivity() throws Exception {


        this.httpRequest("/api/activities/1", HttpMethod.DELETE, null, null, 404);


    }


    @Test
    public void h_modelTestHashCode(){

        Activity activity1 = new Activity();
        activity1.setName("SomeName");
        activity1.setCo2Points(34);
        activity1.setCo2Interval(99);

        Activity activity2 = new Activity();
        activity2.setName("SomeName");
        activity2.setCo2Points(34);
        activity2.setCo2Interval(99);

        assertEquals(activity1, activity2);
        assertEquals(activity1.hashCode(), activity2.hashCode());
    }

    @Test
    public void j_modelTestEquals(){

        Activity activity1 = new Activity();
        activity1.setName("SomeName");
        activity1.setCo2Points(34);
        activity1.setCo2Interval(99);

        Activity activity2 = new Activity();
        activity2.setName("SomeName");
        activity2.setCo2Points(34);
        activity2.setCo2Interval(34);

        assertNotEquals(activity1, activity2);

        assertNotEquals(activity1, null);
    }

    @Test
    public void k_notEqualsNull(){
        assertFalse(standardActivity.equals(null));
    }

    @Test
    public void l_notEqualsDifferentObject(){
        assertFalse(standardActivity.equals(userActivity));
    }

}
