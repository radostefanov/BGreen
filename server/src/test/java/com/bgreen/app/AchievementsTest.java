package com.bgreen.app;


import com.bgreen.app.enums.HttpMethod;
import com.bgreen.app.models.Achievement;
import com.bgreen.app.models.User;
import com.bgreen.app.models.UserActivity;
import com.bgreen.app.models.UserActivityHouse;
import com.bgreen.app.repositories.AchievementRepository;
import com.bgreen.app.repositories.ActivityRepository;
import com.bgreen.app.repositories.UserRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@WithMockUser(username = "demoUser")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AchievementsTest extends AbstractTest {

    Achievement standartAchivement;
    Achievement standartAchivement1;

    @Autowired
    private AchievementRepository achievementRepository;


    @Override
    @Before
    public void setUp(){

        standartAchivement = new Achievement();
        standartAchivement.setAchievementName("Achievement 1");
        standartAchivement.setRequirements(50);

        standartAchivement1 = new Achievement();
        standartAchivement1.setAchievementName("Achievement 1");
        standartAchivement1.setRequirements(50);



        super.setUp();

    }

    @Test
    @Transactional
    public void __clearDatabase(){
        achievementRepository.truncate();
        userRepository.truncate();

    }


    @Test
    public  void a_getAchievementsEmptyTest() throws Exception{

        Achievement[] result = this.httpRequest("/api/achievements", HttpMethod.GET, null, Achievement[].class, 200);

        assertEquals(result.length, 0);
    }

    @Test
    public void b_createNewAchievement() throws Exception{

        //this.httpRequest("/users/", HttpMethod.GET, null, User[].class, 200);

        Achievement result = this.httpRequest("/api/achievements", HttpMethod.POST, standartAchivement, Achievement.class, 200);

        assertEquals(result, standartAchivement);
    }

    @Test
    public void c_getAchievementElementTest() throws Exception {

        Achievement[] result = this.httpRequest("/api/achievements/", HttpMethod.GET, null, Achievement[].class, 200);

        assertEquals(1, result.length);

        assertEquals(result[0], standartAchivement);

    }

    @Test
    public void d_updateAchievement() throws Exception {

        standartAchivement.setAchievementName("Achievement 1 changed");
        standartAchivement.setRequirements(100);


        Achievement result = this.httpRequest("/api/achievements/1", HttpMethod.PUT, standartAchivement, Achievement.class, 200);

        assertEquals(result, standartAchivement);
    }

    @Test
    public void e_getSpecificUpdatedAchievement() throws Exception {

        standartAchivement.setAchievementName("Achievement 1 changed");
        standartAchivement.setRequirements(100);

        Achievement result = this.httpRequest("/api/achievements/1", HttpMethod.GET, null, Achievement.class, 200);

        assertEquals(result, standartAchivement);

    }


    @Test
    public void f_deleteExistingAchievement() throws Exception {


        this.httpRequest("/api/achievements/1", HttpMethod.DELETE, null, null, 200);


    }

    @Test
    public void g_deleteNonExistingAchievement() throws Exception {


        this.httpRequest("/api/achievements/1", HttpMethod.DELETE, null, null, 404);


    }

    @Test
    public void h_achievementTestHashCode() throws Exception {

        Achievement newAchievement = new Achievement();
        newAchievement.setAchievementName("Achievement 1");
        newAchievement.setRequirements(50);

        assertEquals(newAchievement, standartAchivement);
        assertEquals(newAchievement.hashCode(), standartAchivement.hashCode());

        assertNotEquals(newAchievement, null);
        assertEquals(newAchievement, newAchievement);
    }

    @Test
    public void i_assertEquals(){
        assertTrue(standartAchivement.equals(standartAchivement));
        assertTrue(standartAchivement.equals(standartAchivement1));
    }

    @Test
    public void j_assertNotEquals(){
        UserActivity temp = new UserActivityHouse();
        assertFalse(standartAchivement.equals(temp));
        standartAchivement1.setRequirements(10);
        assertFalse(standartAchivement.equals(standartAchivement1));
        standartAchivement1.setAchievementName("banaan");
        assertFalse(standartAchivement.equals(standartAchivement1));
        standartAchivement1.setRequirements(50);
        assertFalse(standartAchivement.equals(standartAchivement1));
    }
}
