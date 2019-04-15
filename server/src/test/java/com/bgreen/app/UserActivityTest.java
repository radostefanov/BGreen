package com.bgreen.app;

import com.bgreen.app.enums.ActivityCategory;
import com.bgreen.app.enums.ActivityType;
import com.bgreen.app.enums.HttpMethod;
import com.bgreen.app.mockmodels.UserActivityDemo;
import com.bgreen.app.models.Activity;
import com.bgreen.app.models.User;
import com.bgreen.app.models.UserActivity;
import com.bgreen.app.models.UserActivityHouse;
import com.bgreen.app.repositories.ActivityRepository;
import com.bgreen.app.repositories.UserActivityRepository;
import com.bgreen.app.reqmodels.UserActivityRequest;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserActivityTest extends AbstractTest {


    User demoUser;

    Activity demoActivity1;
    Activity demoActivity2;
    Activity demoActivity3;
    Activity demoActivity4;
    Activity demoActivity5;
    Activity demoActivity6;
    Activity demoActivity7;
    Activity demoActivity8;

    UserActivity demoUserActivity;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;


    @Override
    @Before
    public void setUp() {

        super.setUp();

        demoUser = new User();
        demoUser.setUsername("user");
        demoUser.setPassword(passwordEncoder.encode("password"));

        demoActivity1 = new Activity();
        demoActivity1.setCo2Interval(5);
        demoActivity1.setCo2Points(2);
        demoActivity1.setName("Activity1");
        demoActivity1.setCategory(ActivityCategory.Home);

        demoActivity2 = new Activity();
        demoActivity2.setCo2Interval(12);
        demoActivity2.setCo2Points(9);
        demoActivity2.setName("Activity2");
        demoActivity2.setCategory(ActivityCategory.Transport);

        demoActivity3 = new Activity();
        demoActivity3.setCo2Interval(0);
        demoActivity3.setCo2Points(6);
        demoActivity3.setName("Activity3");
        demoActivity3.setCategory(ActivityCategory.Transport);

        demoActivity4 = new Activity();
        demoActivity4.setCo2Interval(8);
        demoActivity4.setCo2Points(1);
        demoActivity4.setName("Activity4");
        demoActivity4.setCategory(ActivityCategory.Meal);

        demoActivity5 = new Activity();
        demoActivity5.setCo2Interval(13);
        demoActivity5.setCo2Points(8);
        demoActivity5.setName("Activity5");
        demoActivity5.setCategory(ActivityCategory.SolarPanels);

        demoActivity6 = new Activity();
        demoActivity6.setCo2Interval(1);
        demoActivity6.setCo2Points(2);
        demoActivity6.setName("Activity6");
        demoActivity6.setCategory(ActivityCategory.Insulation);

        demoActivity7 = new Activity();
        demoActivity7.setCo2Interval(1);
        demoActivity7.setCo2Points(2);
        demoActivity7.setName("Activity6");
        demoActivity7.setCategory(ActivityCategory.Default);

        demoActivity8 = new Activity();
        demoActivity8.setCo2Interval(1);
        demoActivity8.setCo2Points(2);
        demoActivity8.setName("Activity8");
        demoActivity8.setCategory(ActivityCategory.LocalProduction);



        demoUserActivity = new UserActivityDemo();
        demoUserActivity.setType(ActivityType.Habit);
        demoUserActivity.setActivity(demoActivity1);
        demoUserActivity.setUser(demoUser);


    }


    @Test
    @Transactional
    public void __clearDatabase(){

        userRepository.truncate();
        activityRepository.truncate();
    }

    @Test
    public void _createUser() throws Exception {

        userRepository.save(demoUser);

        try {
            AbstractTest.AuthToken = this.getLoginToken("user", "password");
            System.out.println("Auth token: " + AbstractTest.AuthToken);
        } catch (Exception e) {

            System.out.println(e);
        }


    }

    @Test
    public void a_verifyExistingUser() throws Exception {


        User[] result = this.httpRequest("/users/", HttpMethod.GET, null, User[].class, 200);

        assertEquals(result.length, 1);

        assertEquals(result[0], demoUser);

    }


    @Test
    public void b_verifyExistingActivities() throws Exception {


        activityRepository.save(demoActivity1);
        activityRepository.save(demoActivity2);
        activityRepository.save(demoActivity3);
        activityRepository.save(demoActivity4);
        activityRepository.save(demoActivity5);
        activityRepository.save(demoActivity6);
        activityRepository.save(demoActivity7);
        activityRepository.save(demoActivity8);

        Activity[] result = this.httpRequest("/api/activities", HttpMethod.GET, null, Activity[].class, 200);

        assertEquals(result.length, 8);

        assertTrue(demoActivity1.equals(result[0]));
        assertTrue(demoActivity2.equals(result[1]));
        assertTrue(demoActivity3.equals(result[2]));
        assertTrue(demoActivity4.equals(result[3]));
        assertTrue(demoActivity5.equals(result[4]));
        assertTrue(demoActivity6.equals(result[5]));
        assertTrue(demoActivity7.equals(result[6]));
        assertTrue(demoActivity8.equals(result[7]));

    }

    @Test
    public void c_connectUserToActivity() throws Exception {


        UserActivityRequest request = new UserActivityRequest();
        request.setType(ActivityType.Once);
        request.setActivityId(Long.valueOf(1));
        request.setDegreesBefore(10);
        request.setDegreesAfter(5);
        User result = this.httpRequest("/api/users/activities", HttpMethod.POST, request, User.class, 200);

        demoUser.setPoints(62.5);
        System.out.println(result);
        assertEquals(result, demoUser);

    }

    @Test
    public void d_verifyConnection() throws Exception {

        UserActivityHouse[] result = this.httpRequest("/api/users/activities", HttpMethod.GET, null, UserActivityHouse[].class, 200);

        assertEquals(result.length, 1);

        System.out.println("Activity");
        System.out.println(result[0].getActivity());

        assertEquals(result[0].getActivity(), demoActivity1);

    }


    @Test
    public void e_addSecondConnection() throws Exception {

        UserActivityRequest request = new UserActivityRequest();
        request.setActivityId(Long.valueOf(2));
        request.setType(ActivityType.Once);
        request.setDistance(10);

        User result = this.httpRequest("/api/users/activities", HttpMethod.POST, request, User.class, 200);

        System.out.println(result);
        demoUser.setPoints(81.5);
        assertEquals(result, demoUser);


    }

    @Test
    public void f_verifyConnection() throws Exception {

        UserActivityDemo[] result = this.httpRequest("/api/users/activities", HttpMethod.GET, null, UserActivityDemo[].class, 200);

        assertEquals(result.length, 2);

        assertTrue(demoActivity1.equals(result[0].getActivity()) || demoActivity2.equals(result[0].getActivity()));
        assertTrue(demoActivity1.equals(result[1].getActivity()) || demoActivity2.equals(result[1].getActivity()));



    }

    @Test
    public void fa_connectThirdActivity() throws Exception {

        UserActivityRequest request = new UserActivityRequest();
        request.setActivityId(Long.valueOf(3));
        request.setType(ActivityType.Once);
        request.setDistance(10);

        User result = this.httpRequest("/api/users/activities", HttpMethod.POST, request, User.class, 200);

        demoUser.setPoints(92.5);
        assertEquals(result, demoUser);


    }

    @Test
    public void fb_verifyConnection() throws Exception {

        UserActivityDemo[] result = this.httpRequest("/api/users/activities", HttpMethod.GET, null, UserActivityDemo[].class, 200);

        assertEquals(result.length, 3);

        assertTrue(demoActivity1.equals(result[0].getActivity()) || demoActivity3.equals(result[0].getActivity()));
        assertTrue(demoActivity1.equals(result[1].getActivity()) || demoActivity2.equals(result[1].getActivity()));
        assertTrue(demoActivity1.equals(result[2].getActivity()) || demoActivity3.equals(result[2].getActivity()));



    }



    @Test
    public void fca_connectFourthActivity() throws Exception {

        UserActivityRequest request = new UserActivityRequest();
        request.setActivityId(Long.valueOf(4));
        request.setType(ActivityType.Once);
        request.setVeganCoefficient(100);

        User result = this.httpRequest("/api/users/activities", HttpMethod.POST, request, User.class, 200);

        demoUser.setPoints(122.5);
        assertEquals(result, demoUser);


    }

    @Test
    public void fcb_verifyConnection() throws Exception {

        UserActivityDemo[] result = this.httpRequest("/api/users/activities", HttpMethod.GET, null, UserActivityDemo[].class, 200);

        assertEquals(result.length, 4);

        assertTrue(demoActivity1.equals(result[0].getActivity()) || demoActivity3.equals(result[0].getActivity()));
        assertTrue(demoActivity1.equals(result[1].getActivity()) || demoActivity2.equals(result[1].getActivity()));
        assertTrue(demoActivity1.equals(result[2].getActivity()) || demoActivity3.equals(result[2].getActivity()));
        assertTrue(demoActivity1.equals(result[3].getActivity()) || demoActivity4.equals(result[3].getActivity()));



    }

    @Test
    public void fda_connectFifthActivity() throws Exception {

        UserActivityRequest request = new UserActivityRequest();
        request.setActivityId(Long.valueOf(5));
        request.setType(ActivityType.Once);
        request.setEnergySavedSolar(0.2);

        User result = this.httpRequest("/api/users/activities", HttpMethod.POST, request, User.class, 200);

        demoUser.setPoints(1173.7);
        assertEquals(result, demoUser);
    }

    @Test
    public void fdb_testConnection() throws Exception {


        UserActivityDemo[] result = this.httpRequest("/api/users/activities", HttpMethod.GET, null, UserActivityDemo[].class, 200);

        assertEquals(result.length, 5);

        assertTrue(demoActivity1.equals(result[0].getActivity()) || demoActivity3.equals(result[0].getActivity()));
        assertTrue(demoActivity1.equals(result[1].getActivity()) || demoActivity2.equals(result[1].getActivity()));
        assertTrue(demoActivity1.equals(result[2].getActivity()) || demoActivity3.equals(result[2].getActivity()));
        assertTrue(demoActivity1.equals(result[3].getActivity()) || demoActivity4.equals(result[3].getActivity()));
        assertTrue(demoActivity1.equals(result[4].getActivity()) || demoActivity5.equals(result[4].getActivity()));

    }

    @Test
    public void fea_connectSixthActivity() throws Exception {

        UserActivityRequest request = new UserActivityRequest();
        request.setActivityId(Long.valueOf(6));
        request.setType(ActivityType.Once);
        request.setAreaInsulated(10);

        User result = this.httpRequest("/api/users/activities", HttpMethod.POST, request, User.class, 200);

        demoUser.setPoints(1189.7);
        assertEquals(result, demoUser);
    }
    @Test
    public void feb_testConnection() throws Exception {


        UserActivityDemo[] result = this.httpRequest("/api/users/activities", HttpMethod.GET, null, UserActivityDemo[].class, 200);

        assertEquals(result.length, 6);

        assertTrue(demoActivity1.equals(result[0].getActivity()) || demoActivity3.equals(result[0].getActivity()));
        assertTrue(demoActivity1.equals(result[1].getActivity()) || demoActivity2.equals(result[1].getActivity()));
        assertTrue(demoActivity1.equals(result[2].getActivity()) || demoActivity3.equals(result[2].getActivity()));
        assertTrue(demoActivity1.equals(result[3].getActivity()) || demoActivity4.equals(result[3].getActivity()));
        assertTrue(demoActivity1.equals(result[4].getActivity()) || demoActivity5.equals(result[4].getActivity()));
        assertTrue(demoActivity1.equals(result[5].getActivity()) || demoActivity6.equals(result[5].getActivity()));

    }

    @Test
    public void fga_addLocalProduction() throws Exception {


        UserActivityRequest request = new UserActivityRequest();
        request.setActivityId(Long.valueOf(8));
        request.setType(ActivityType.Once);
        request.setMassBought(20);

        User result = this.httpRequest("/api/users/activities", HttpMethod.POST, request, User.class, 200);

        demoUser.setPoints(1189.7);
        assertEquals(result, demoUser);

    }

    @Test
    public void fgb_verifyLocalProduction() throws Exception {


        UserActivityDemo[] result = this.httpRequest("/api/users/activities", HttpMethod.GET, null, UserActivityDemo[].class, 200);

        assertEquals(result.length, 7);

        assertTrue(demoActivity1.equals(result[0].getActivity()) || demoActivity3.equals(result[0].getActivity()));
        assertTrue(demoActivity1.equals(result[1].getActivity()) || demoActivity2.equals(result[1].getActivity()));
        assertTrue(demoActivity1.equals(result[2].getActivity()) || demoActivity3.equals(result[2].getActivity()));
        assertTrue(demoActivity1.equals(result[3].getActivity()) || demoActivity4.equals(result[3].getActivity()));
        assertTrue(demoActivity1.equals(result[4].getActivity()) || demoActivity5.equals(result[4].getActivity()));
        assertTrue(demoActivity1.equals(result[5].getActivity()) || demoActivity6.equals(result[5].getActivity()));
        assertTrue(demoActivity1.equals(result[6].getActivity()) || demoActivity8.equals(result[6].getActivity()));
    }

    @Test
    public void fx_testDefaultSwitchCase() throws Exception{
            UserActivityRequest request = new UserActivityRequest();
            request.setActivityId(Long.valueOf(7));
            request.setType(ActivityType.Once);

            User result = this.httpRequest("/api/users/activities", HttpMethod.POST, request, User.class, 400);

            assertEquals(result, demoUser);
    }


    @Test
    public void g_deleteConnection() throws Exception {

        this.httpRequest("/api/users/activities/2", HttpMethod.DELETE, null, null, 200);

    }

    @Test
    public void h_verifyDeletion() throws Exception {

        UserActivityHouse[] result = this.httpRequest("/api/users/activities", HttpMethod.GET, null, UserActivityHouse[].class, 200);

        assertEquals(result.length, 6);

        assertEquals(result[0].getActivity(), demoActivity1);
    }

    @Test
    public void i_deleteSecondConnection() throws Exception {

        this.httpRequest("/api/users/activities/1", HttpMethod.DELETE, null, null, 200);

    }

    @Test
    public void j_verifyOneEntity() throws Exception {

        UserActivityHouse[] result = this.httpRequest("/api/users/activities", HttpMethod.GET, null, UserActivityHouse[].class, 200);

        assertEquals(result.length, 5);

    }

    @Test
    public void ja_unauthorizedDeletion() throws Exception{


        demoUser = new User();
        demoUser.setUsername("User2");
        demoUser.setPassword(passwordEncoder.encode("password"));


        userRepository.save(demoUser);

        try {
            AbstractTest.AuthToken = this.getLoginToken("User2", "password");
            System.out.println("Auth token: " + AbstractTest.AuthToken);
        } catch (Exception e) {

            System.out.println(e);
        }

        this.httpRequest("/api/users/activities/3", HttpMethod.DELETE, null, null, 401);
    }

    @Test
    public void jb_deleteRhidAndVerify() throws Exception {

        try {
            AbstractTest.AuthToken = this.getLoginToken("user", "password");
            System.out.println("Auth token: " + AbstractTest.AuthToken);
        } catch (Exception e) {

            System.out.println(e);
        }

        this.httpRequest("/api/users/activities/3", HttpMethod.DELETE, null, null, 200);
        this.httpRequest("/api/users/activities/4", HttpMethod.DELETE, null, null, 200);
        this.httpRequest("/api/users/activities/5", HttpMethod.DELETE, null, null, 200);
        this.httpRequest("/api/users/activities/6", HttpMethod.DELETE, null, null, 200);
        this.httpRequest("/api/users/activities/7", HttpMethod.DELETE, null, null, 200);


        Activity[] result = this.httpRequest("/api/users/activities", HttpMethod.GET, null, Activity[].class, 200);

        assertEquals(result.length, 0);

    }

    @Test
    public void k_getterSetterTest(){

        assertEquals(demoUserActivity.getUser(), demoUser);
        assertEquals(demoUserActivity.getActivity(), demoActivity1);
        assertEquals(demoUserActivity.getType(), ActivityType.Habit);
    }

    @Test
    public void l_modelTestGetUsers(){



        Set<UserActivity> users = new HashSet<UserActivity>();
        users.add(demoUserActivity);

        demoActivity1.setUsers(users);
        demoUser.setActivities(users);

        assertEquals(demoActivity1.getUsers(), users);
        assertEquals(demoUser.getActivities(), users);
    }




}