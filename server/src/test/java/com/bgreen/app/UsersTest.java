package com.bgreen.app;


import com.bgreen.app.controllers.UserController;
import com.bgreen.app.enums.ActivityCategory;
import com.bgreen.app.enums.ActivityType;
import com.bgreen.app.enums.HttpMethod;
import com.bgreen.app.mockmodels.UserActivityDemo;
import com.bgreen.app.models.Activity;
import com.bgreen.app.models.BasicHttpResponse;
import com.bgreen.app.models.Friend;
import com.bgreen.app.models.User;
import com.bgreen.app.models.UserActivity;
import com.bgreen.app.models.UserActivityHouse;
import com.bgreen.app.repositories.UserRepository;
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
public class UsersTest extends AbstractTest {

    User standardUser;

    private double delta = 0.2;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Before
    public void setUp() {

        super.setUp();

        standardUser = new User();
        standardUser.setEmail("User1@test.com");
        standardUser.setUsername("User1");
        standardUser.setPassword("Password");
        standardUser.setPoints(50);
        standardUser.setProfile_picture("picture");

    }


    @Test
    @Transactional
    public void __clearDatabase(){

        userRepository.truncate();
    }

    @Test
    public void a_getEmptySetOfUsers() throws Exception {

        User[] result;
        result = this.httpRequest("/users", HttpMethod.GET, null, User[].class, 200);

        assertEquals(result.length, 0); // Expect one because of the auth login user

    }


    @Test
    public void b_createNewUser() throws Exception {

        User newUser = new User();

        newUser.setUsername("User1");
        newUser.setPassword(passwordEncoder.encode("Password"));
        newUser.setEmail("User1@test.com");
        newUser.setPoints(50);

        User result = this.httpRequest("/users", HttpMethod.POST, newUser, User.class, 200);

        assertEquals(result.getUsername(), newUser.getUsername());
        assertEquals(result.getPassword(), newUser.getPassword());
        assertEquals(result.getEmail(), newUser.getEmail());
        assertTrue(result.getPoints() == newUser.getPoints());
    }

    @Test
    public void c_getUser1ElementTest() throws Exception {

        User[] result = this.httpRequest("/users", HttpMethod.GET, null, User[].class, 200);

        assertEquals(1, result.length);

        assertEquals(result[0], standardUser);

    }

    @Test
    public void d_updateUser() throws Exception {

        standardUser.setUsername("UserUpdate");
        standardUser.setPassword(passwordEncoder.encode("PasswordUpdate"));
        standardUser.setEmail("UserUpdate@test.com");
        standardUser.setPoints(100);

        try {
            AbstractTest.AuthToken = this.getLoginToken("User1", "Password");
        } catch (Exception e) {

            System.out.println(e);
        }

        User result = this.httpRequest("/api/users/info", HttpMethod.PUT, standardUser, User.class, 200);

        assertEquals(result, standardUser);

    }

    @Test
    public void d_updateUserGoals() throws Exception {
        standardUser.setUsername("UserUpdate");
        standardUser.setPassword(null);
        standardUser.setEmail("UserUpdate@test.com");
        standardUser.setPoints(100);
        standardUser.setDailyGoal(20.2);
        standardUser.setWeeklyGoal(30.2);
        standardUser.setMonthlyGoal(40.2);

        User result = this.httpRequest("/api/users/info", HttpMethod.PUT, standardUser, User.class, 200);

        assertEquals(20.2, result.getDailyGoal(), delta);

        assertEquals(30.2, result.getWeeklyGoal(), delta);

        assertEquals(40.2, result.getMonthlyGoal(), delta);

    }

    @Test
    public void e_getSpecificUpdatedUser() throws Exception {

        standardUser.setUsername("UserUpdate");
        standardUser.setPassword(passwordEncoder.encode("PasswordUpdate"));
        standardUser.setEmail("UserUpdate@test.com");
        standardUser.setPoints(100);

        User result = this.httpRequest("/api/users/info", HttpMethod.GET, null, User.class, 200);

        assertEquals(result, standardUser);
    }


    @Test
    public void f_deleteExistingUser() throws Exception {

        this.httpRequest("/users/1", HttpMethod.DELETE, null, null, 200);

    }

    @Test
    public void g_deleteNonExistingUser() throws Exception {

        this.httpRequest("/users/1", HttpMethod.DELETE, null, null, 404);

    }

    @Test
    public void j_registerUserSuccess() throws Exception {

        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setEmail("email@mail.com");
        user.setPoints(0);

        BasicHttpResponse response = this.httpRequest("/api/auth/register", HttpMethod.POST, user, BasicHttpResponse.class, 200);

        assertEquals(response.getStatus(), ("success"));

    }

    @Test
    public void j_registerUserFailEmail() throws Exception {

        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setEmail("mail");
        user.setPoints(0);

        this.httpRequest("/api/auth/register", HttpMethod.POST, user, null, 400);

    }

    @Test
    public void k_registerAlreadyExists() throws Exception {

        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setEmail("mail@test.com");
        user.setPoints(0);

        this.httpRequest("/api/auth/register", HttpMethod.POST, user, BasicHttpResponse.class, 200);
        BasicHttpResponse response = this.httpRequest("/api/auth/register", HttpMethod.POST, user, BasicHttpResponse.class, 200);

        assertTrue(response.getMessage().contains("exists"));

    }

    @Test
    public void l_LoginUserSuccess() throws Exception {

        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setEmail("mail@test.com");
        user.setPoints(0);

        this.httpRequest("/api/auth/register", HttpMethod.POST, user, BasicHttpResponse.class, 200);

        BasicHttpResponse response = this.httpRequest("/api/auth/login", HttpMethod.POST, user, BasicHttpResponse.class, 200);

        assertEquals(response.getStatus(), ("success"));

    }

    @Test
    public void l_LoginUserFailUsername() throws Exception {

        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setEmail("mail@test.com");
        user.setPoints(0);

        this.httpRequest("/api/auth/register", HttpMethod.POST, user, BasicHttpResponse.class, 200);

        user.setUsername("newUsername");

        BasicHttpResponse response = this.httpRequest("/api/auth/login", HttpMethod.POST, user, BasicHttpResponse.class, 200);

        assertEquals(response.getStatus(), ("fail"));

    }

    @Test
    public void l_LoginUserFailPassword() throws Exception {

        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setEmail("mail@test.com");
        user.setPoints(0);

        this.httpRequest("/api/auth/register", HttpMethod.POST, user, BasicHttpResponse.class, 200);

        user.setPassword("newPassword");

        BasicHttpResponse response = this.httpRequest("/api/auth/login", HttpMethod.POST, user, BasicHttpResponse.class, 200);

        assertEquals(response.getStatus(), ("fail"));

    }

    @Test
    public void x_getActivitiesTest(){
        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setEmail("mail@test.com");
        user.setPoints(0);

        Activity activity = new Activity();
        activity.setCategory(ActivityCategory.Home);
        activity.setCo2Interval(1);
        activity.setCo2Points(2);
        activity.setName("abc");

        Set<UserActivity> testSet = new HashSet<>();

        UserActivity userActivity = new UserActivityDemo();
        userActivity.setActivity(activity);
        userActivity.setUser(user);
        userActivity.setType(ActivityType.Habit);

        testSet.add(userActivity);

        user.setActivities(testSet);


        assertEquals(user.getActivities(), testSet);
    }

    @Test
    public void y_setActivitiesTest(){
        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setEmail("mail@test.com");
        user.setPoints(0);

        Activity activity = new Activity();
        activity.setCategory(ActivityCategory.Home);
        activity.setCo2Interval(1);
        activity.setCo2Points(2);
        activity.setName("abc");

        Activity activity2 = new Activity();
        activity.setCategory(ActivityCategory.Home);
        activity.setCo2Interval(12);
        activity.setCo2Points(22);
        activity.setName("abcd");

        Set<UserActivity> testSet = new HashSet<>();
        Set<UserActivity> testSet2 = new HashSet<>();

        UserActivity userActivity = new UserActivityDemo();
        userActivity.setActivity(activity);
        userActivity.setUser(user);
        userActivity.setType(ActivityType.Habit);

        UserActivity userActivity2 = new UserActivityDemo();
        userActivity.setActivity(activity2);
        userActivity.setUser(user);
        userActivity.setType(ActivityType.Habit);

        testSet.add(userActivity);
        testSet2.add(userActivity2);

        user.setActivities(testSet);
        user.setActivities(testSet2);

        assertEquals(user.getActivities(), testSet2);
    }

    @Test
    public void y_modelToStringTest(){

        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setEmail("mail@test.com");
        user.setPoints(0);

        assertEquals(user.toString(), "username/password");
    }

    @Test
    public void z_getUsernameTest(){

        assertEquals(standardUser.getUsername(), "User1");
        assertNotEquals(standardUser.getUsername(), "User2");
    }

    @Test
    public void z_setUsernameTest(){

        standardUser.setUsername("User2");
        assertEquals(standardUser.getUsername(), "User2");
    }

    @Test
    public void z_getPasswordTest(){

        assertEquals(standardUser.getPassword(), "Password");
        assertNotEquals(standardUser.getPassword(), "Password2");
    }

    @Test
    public void z_setPasswordTest(){

        standardUser.setPassword("Password2");
        assertEquals(standardUser.getPassword(), "Password2");
    }

    @Test
    public void z_getEmailTest(){

        assertEquals(standardUser.getEmail(), "User1@test.com");
        assertNotEquals(standardUser.getEmail(), "mail");
    }

    @Test
    public void z_setEmailTest(){

        standardUser.setEmail("User2@test.com");
        assertEquals(standardUser.getEmail(), "User2@test.com");
    }

    @Test
    public void z_getProfilePictureTest(){

        assertEquals(standardUser.getProfilePicture(), "picture");
        assertNotEquals(standardUser.getProfilePicture(), "profile");
    }

    @Test
    public void z_setProfilePictureTest(){

        standardUser.setProfile_picture("picture2");
        assertEquals(standardUser.getProfilePicture(), "picture2");
    }

    @Test
    public void z_getPointsTest(){

        assertTrue(standardUser.getPoints() == 50);
        assertNotEquals(standardUser.getEmail(), 100);
    }

    @Test
    public void z_setPointsTest(){

        standardUser.setPoints(200);
        assertTrue(standardUser.getPoints() == 200);
    }

    @Test
    public void z_getDailyGoalTest() {
        standardUser.setDailyGoal(20.2);
        assertEquals(20.2, standardUser.getDailyGoal(), delta);
    }

    @Test
    public void z_getWeeklyGoalTest() {
        standardUser.setWeeklyGoal(30.2);
        assertEquals(30.2, standardUser.getWeeklyGoal(), delta);
    }

    @Test
    public void z_getMonthlyGoalTest() {
        standardUser.setMonthlyGoal(40.2);
        assertEquals(40.2, standardUser.getMonthlyGoal(), delta);
    }

    @Test
    public void z_equalsTest(){
        UserActivity temp = new UserActivityHouse();

        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setEmail("mail@test.com");
        user.setPoints(0);

        User user1 = new User();
        user1.setPassword("password");
        user1.setUsername("username");
        user1.setEmail("mail@test.com");
        user1.setPoints(0);

        assertTrue(user.equals(user));

        assertFalse(user.equals(null));
        assertFalse(user.equals(temp));

        assertTrue(user.equals(user1));
        assertEquals(user.hashCode(), user1.hashCode());
        user1.setUsername("newUsername");
        assertFalse(user.equals(user1));
        assertFalse(user.hashCode() == user1.hashCode());
        user1.setUsername("username");
        user1.setEmail("banaan");
        assertFalse(user.equals(user1));
        user1.setUsername("banaankwadraat");
        assertFalse(user.equals(user1));
    }

    @Test
    public void za_blankEmailHash(){
        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setEmail("mail@test.com");
        user.setPoints(1);

        User user1 = new User();
        user1.setPassword("password");
        user1.setUsername("username");
        user1.setEmail(null);
        user1.setPoints(1);

        assertFalse(user.hashCode() == user1.hashCode());

    }

    @Test
    public void zb_testFriendsEqual(){

        Friend fr = new Friend();
        assertEquals(fr, fr);
    }


}
