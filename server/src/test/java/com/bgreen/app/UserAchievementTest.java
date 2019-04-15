package com.bgreen.app;

import com.bgreen.app.enums.HttpMethod;
import com.bgreen.app.models.Achievement;
import com.bgreen.app.models.User;
import com.bgreen.app.models.UserAchievement;
import com.bgreen.app.repositories.AchievementRepository;
import com.bgreen.app.repositories.UserAchievementRepository;
import com.bgreen.app.reqmodels.UserAchievementRequest;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAchievementTest extends AbstractTest{

    User demoUser;

    Achievement demoAchievement1;
    Achievement demoAchievement2;
    Achievement demoAchievement3;

    UserAchievement demoUserAchievement;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private UserAchievementRepository userAchievementRepository;

    @Override
    @Before
    public void setUp(){

        super.setUp();

        demoUser = new User();
        demoUser.setUsername("user");
        demoUser.setPassword(passwordEncoder.encode("password"));

        demoAchievement1 = new Achievement();
        demoAchievement1.setRequirements(50);
        demoAchievement1.setAchievementName("Achievement 1");

        demoAchievement2 = new Achievement();
        demoAchievement2.setRequirements(60);
        demoAchievement2.setAchievementName("Achievement 2");

        demoAchievement3 = new Achievement();
        demoAchievement3.setRequirements(70);
        demoAchievement3.setAchievementName("Achievement 3");

        demoUserAchievement = new UserAchievement();
        demoUserAchievement.setAchievement(demoAchievement1);
        demoUserAchievement.setUser(demoUser);
    }

    @Test
    @Transactional
    public void __clearDatabase(){

        userRepository.truncate();
        achievementRepository.truncate();
    }

    @Test
    public void _createUser() throws Exception {

        userRepository.save(demoUser);

        try{
            AbstractTest.AuthToken = this.getLoginToken("user", "password");
            System.out.println("Auth token: " + AbstractTest.AuthToken);
        } catch (Exception e){
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
    public void b_verifyExistingAchievements() throws Exception {


        achievementRepository.save(demoAchievement1);
        achievementRepository.save(demoAchievement2);
        achievementRepository.save(demoAchievement3);

        Achievement[] result = this.httpRequest("/api/achievements", HttpMethod.GET, null, Achievement[].class, 200);

        assertEquals(result.length, 3);

        assertTrue(demoAchievement1.equals(result[0]));
        assertTrue(demoAchievement2.equals(result[1]));
        assertTrue(demoAchievement3.equals(result[2]));
    }

    @Test
    public void c_connectUserToAchievement() throws Exception {

        UserAchievementRequest request = new UserAchievementRequest();
        request.setActhievementId(Long.valueOf(1));
        request.setAchievementName("Achievement 1");


        User result = this.httpRequest("/api/users/achievements", HttpMethod.POST, request, User.class, 200);

        assertEquals(result, demoUser);
    }

   

    @Test
    public void d_verifyConnection() throws Exception {

        UserAchievement[] result = this.httpRequest("/api/users/achievements", HttpMethod.GET, null, UserAchievement[].class, 200);

        assertEquals(result.length, 1);

        System.out.println("Achievement");
        System.out.println(result[0].getAchievement());

        assertEquals(result[0].getAchievement(), demoAchievement1);

    }


    @Test
    public void e_addSecondConnection() throws Exception {

        UserAchievementRequest request = new UserAchievementRequest();
        request.setActhievementId(Long.valueOf(2));
        request.setAchievementName("Acievement 2");

        User result = this.httpRequest("/api/users/achievements", HttpMethod.POST, request, User.class, 200);

        System.out.println(result);
        assertEquals(result, demoUser);

    }

    @Test
    public void f_verifyConnection() throws Exception {

        UserAchievement[] result = this.httpRequest("/api/users/achievements", HttpMethod.GET, null, UserAchievement[].class, 200);

        assertEquals(result.length, 2);

        assertTrue(demoAchievement1.equals(result[0].getAchievement()) || demoAchievement2.equals(result[0].getAchievement()));
        assertTrue(demoAchievement1.equals(result[1].getAchievement()) || demoAchievement2.equals(result[1].getAchievement()));

    }

    @Test
    public void g_deleteAchievements() throws Exception {

        this.httpRequest("/api/users/achievements", HttpMethod.DELETE, null, null, 200);

    }

    @Test
    public void h_verifyDeletion() throws Exception {

        UserAchievement[] result = this.httpRequest("/api/users/achievements", HttpMethod.GET, null, UserAchievement[].class, 200);

        assertEquals(result.length, 0);
    }
}
