package com.bgreen.app;

import com.bgreen.app.models.User;
import com.bgreen.app.repositories.AchievementRepository;
import com.bgreen.app.repositories.ActivityRepository;
import com.bgreen.app.repositories.UserRepository;
import com.bgreen.app.seeders.DatabaseSeeder;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseSeederTest extends AbstractTest{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private ActivityRepository activityRepository;

    private DatabaseSeeder seeder;

    private ContextRefreshedEvent event;

    @Autowired
    private ApplicationContext appContext;

    private User user;

    @Test
    @Transactional
    public void _clearDatabase(){

        userRepository.truncate();
        achievementRepository.truncate();
        activityRepository.truncate();
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();

        user = new User();
        user.setUsername("admin");
        user.setEmail("admin@test.com");
        user.setPassword(new BCryptPasswordEncoder().encode("pwd"));

        event = new ContextRefreshedEvent(appContext);
//        userRepository.truncate();
//        achievementRepository.truncate();
//        activityRepository.truncate();

        seeder = new DatabaseSeeder(userRepository, achievementRepository, activityRepository);
        seeder.seed(event);

    }

    @Test
    public void testSeedingTest(){
        List<User> list = userRepository.findAll();

        assertEquals(list.get(0), user);
    }

    @Test
    public void testNotSeedingTest(){
        seeder.seed(event);

        List<User> list = userRepository.findAll();

        assertEquals(list.get(0), user);
    }



}
