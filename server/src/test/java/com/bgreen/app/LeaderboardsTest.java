package com.bgreen.app;

import com.bgreen.app.enums.ActivityCategory;
import com.bgreen.app.enums.HttpMethod;
import com.bgreen.app.models.Activity;
import com.bgreen.app.models.Friend;
import com.bgreen.app.models.User;
import com.bgreen.app.repositories.ActivityRepository;
import com.bgreen.app.repositories.UserRepository;
import com.bgreen.app.reqmodels.UserFriend;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.Assert.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LeaderboardsTest extends AbstractTest{

    User demoUser1;
    User demoUser2;
    User demoUser3;
    User demoUser4;
    User demoUser5;
    User demoUser6;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Before
    public void setUp() {

        demoUser1 = new User();
        demoUser1.setUsername("user");
        demoUser1.setPassword(passwordEncoder.encode("password"));
        demoUser1.setPoints(10);

        demoUser2 = new User();
        demoUser2.setUsername("user2");
        demoUser2.setPassword(passwordEncoder.encode("password2"));
        demoUser2.setPoints(20);

        demoUser3 = new User();
        demoUser3.setUsername("user3");
        demoUser3.setPassword(passwordEncoder.encode("password3"));
        demoUser3.setPoints(30);

        demoUser4 = new User();
        demoUser4.setUsername("user4");
        demoUser4.setPassword(passwordEncoder.encode("password4"));
        demoUser4.setPoints(40);

        demoUser5 = new User();
        demoUser5.setUsername("user5");
        demoUser5.setPassword(passwordEncoder.encode("password5"));
        demoUser5.setPoints(50);

        demoUser6 = new User();
        demoUser6.setUsername("user6");
        demoUser6.setPassword(passwordEncoder.encode("password6"));
        demoUser6.setPoints(60);

        super.setUp();
    }

    @Test
    @Transactional
    public void __clearDatabase(){
        userRepository.truncate();
    }

    @Test
    @WithMockUser
    public void a_top5GlobalUsersTest() throws Exception{
        userRepository.save(demoUser1);
        userRepository.save(demoUser2);
        userRepository.save(demoUser3);
        userRepository.save(demoUser4);
        userRepository.save(demoUser5);
        userRepository.save(demoUser6);

        List<User> correctResultList = new ArrayList<>();
        correctResultList.add(demoUser6);
        correctResultList.add(demoUser5);
        correctResultList.add(demoUser4);
        correctResultList.add(demoUser3);
        correctResultList.add(demoUser2);

        correctResultList.add(demoUser6);
        UserFriend[] resultList = this.httpRequest("/api/leaderboards/global",
                HttpMethod.GET, null, UserFriend[].class, 200);


        assertTrue(resultList[0].equals(new UserFriend(demoUser6)));
        assertTrue(resultList[1].equals(new UserFriend(demoUser5)));
        assertTrue(resultList[2].equals(new UserFriend(demoUser4)));
        assertTrue(resultList[3].equals(new UserFriend(demoUser3)));
        assertTrue(resultList[4].equals(new UserFriend(demoUser2)));

    }

}
