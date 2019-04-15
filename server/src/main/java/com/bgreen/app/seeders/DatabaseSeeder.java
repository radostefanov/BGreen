package com.bgreen.app.seeders;

import com.bgreen.app.enums.ActivityCategory;
import com.bgreen.app.models.Achievement;
import com.bgreen.app.models.Activity;
import com.bgreen.app.models.User;
import com.bgreen.app.repositories.AchievementRepository;
import com.bgreen.app.repositories.ActivityRepository;
import com.bgreen.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This is our database seeder, we will only use this during our testing time.
 * This seeder will become useless after uploading this application to a server.
 * This is also the reason it doesn't have to be tested :)
 */
@Component
public class DatabaseSeeder {

    /**
     * Private fields.
     */


    private UserRepository userRepository;
    private AchievementRepository achievementRepository;

    private ActivityRepository activityRepository;

    /**
     * Class constructor.
     * @param userRepository take the userRepository.
     * @param achievementRepository take the achievementRepository.
     */
    @Autowired
    public DatabaseSeeder(UserRepository userRepository,
                          AchievementRepository achievementRepository,
                          ActivityRepository activityRepository) {
        this.userRepository = userRepository;
        this.achievementRepository = achievementRepository;
        this.activityRepository = activityRepository;
    }

    /**
     * EventListener so spring will set it up during startup.
     * @param event the event.
     */
    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsersTable();
        seedAchievementsTable();
        seedActivitiesTable();
    }

    /**
     * The seeder for the users table.
     */
    private void seedUsersTable() {
        User user = userRepository.findUserByUsername("admin");

        //if no user is found with username admin, it will create one.
        if (user == null) {
            User tempUser = new User();
            tempUser.setUsername("admin");
            tempUser.setEmail("admin@test.com");
            tempUser.setPassword(new BCryptPasswordEncoder().encode("pwd"));
            userRepository.save(tempUser);
            System.out.println("Server: the default admin account has been seeded successfully.");
        } else {
            //if it can find an admin account it will tell you.
            System.out.println("Server: the default admin account seeding was not required.");
        }
    }

    /**
     * The seeder for the activities table.
     */
    private void seedActivitiesTable() {

        if (activityRepository.findAll().size() == 0) {

            Activity activity = new Activity();
            activity.setName("Eat vegetarian / vegan meal");
            activity.setCategory(ActivityCategory.Meal);
            activity.setCo2Points(5);
            activity.setCo2Interval(0);

            activityRepository.save(activity);

            activity = new Activity();
            activity.setName("Travel with bike");
            activity.setCategory(ActivityCategory.Transport);
            activity.setCo2Points(9);
            activity.setCo2Interval(0);

            activityRepository.save(activity);

            activity = new Activity();
            activity.setName("Travel with public transport");
            activity.setCategory(ActivityCategory.Transport);
            activity.setCo2Points(6);
            activity.setCo2Interval(0);

            activityRepository.save(activity);

            activity = new Activity();
            activity.setName("Lower temperature at home");
            activity.setCategory(ActivityCategory.Home);
            activity.setCo2Points(40);
            activity.setCo2Interval(0);

            activityRepository.save(activity);

            activity = new Activity();
            activity.setName("Installed solar panels");
            activity.setCategory(ActivityCategory.SolarPanels);
            activity.setCo2Points(200);
            activity.setCo2Interval(0);

            activityRepository.save(activity);

            activity = new Activity();
            activity.setName("Insulated house");
            activity.setCategory(ActivityCategory.Insulation);
            activity.setCo2Points(150);
            activity.setCo2Interval(0);

            activityRepository.save(activity);


            activity = new Activity();
            activity.setName("Buy local produce");
            activity.setCategory(ActivityCategory.LocalProduction);
            activity.setCo2Points(150);
            activity.setCo2Interval(0);

            activityRepository.save(activity);
        } else {
            System.out.println("Server: the activity seeding was not required.");

        }


    }

    /**
     * The seeder for the achievements table.
     */
    private void seedAchievementsTable() {
        //If more achievements are added the size it should equal should be edited as well.
        if (achievementRepository.findAll().size() != 7) {

            Achievement tempAchievement = new Achievement();
            tempAchievement.setAchievementName("10 points");
            tempAchievement.setRequirements(10);
            achievementRepository.save(tempAchievement);

            tempAchievement = new Achievement();
            tempAchievement.setAchievementName("50 points");
            tempAchievement.setRequirements(50);
            achievementRepository.save(tempAchievement);

            tempAchievement = new Achievement();
            tempAchievement.setAchievementName("100 points");
            tempAchievement.setRequirements(100);
            achievementRepository.save(tempAchievement);

            tempAchievement = new Achievement();
            tempAchievement.setAchievementName("500 points");
            tempAchievement.setRequirements(500);
            achievementRepository.save(tempAchievement);

            tempAchievement = new Achievement();
            tempAchievement.setAchievementName("1000 points");
            tempAchievement.setRequirements(1000);
            achievementRepository.save(tempAchievement);

            tempAchievement = new Achievement();
            tempAchievement.setAchievementName("5000 points");
            tempAchievement.setRequirements(5000);
            achievementRepository.save(tempAchievement);

            tempAchievement = new Achievement();
            tempAchievement.setAchievementName("10000 points");
            tempAchievement.setRequirements(10000);
            achievementRepository.save(tempAchievement);
            System.out.println("Server: the achievements have been seeded successfully.");
        } else {
            //All the achievements are in place.
            System.out.println("Server: the achievements seeding was not required.");
        }
    }
}
