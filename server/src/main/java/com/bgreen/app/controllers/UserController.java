package com.bgreen.app.controllers;

import com.bgreen.app.configuration.JwtConfig;
import com.bgreen.app.enums.ActivityType;
import com.bgreen.app.models.BasicHttpResponse;
import com.bgreen.app.models.User;
import com.bgreen.app.models.UserAchievement;
import com.bgreen.app.models.UserActivity;
import com.bgreen.app.models.UserActivityHouse;
import com.bgreen.app.models.UserActivityInsulation;
import com.bgreen.app.models.UserActivityLocalProduction;
import com.bgreen.app.models.UserActivityMeal;
import com.bgreen.app.models.UserActivitySolarPanels;
import com.bgreen.app.models.UserActivityTravel;
import com.bgreen.app.repositories.AchievementRepository;
import com.bgreen.app.repositories.ActivityRepository;
import com.bgreen.app.repositories.UserAchievementRepository;
import com.bgreen.app.repositories.UserActivityRepository;
import com.bgreen.app.repositories.UserRepository;
import com.bgreen.app.reqmodels.UserAchievementRequest;
import com.bgreen.app.reqmodels.UserActivityRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * UserController.
 * Controller used for rendering user related requests.
 */
@RestController
public class UserController {

    /**
     * Creates userRepository.
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    /**
     * Creates activityRepository.
     */
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserAchievementRepository userAchievementRepository;

    /**
     * Creates userActivityRepository.
     */
    @Autowired
    private UserActivityRepository userActivityRepository;

    /**
     * Creates a passwordEncoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Get a list of all Users.
     *
     * @return List with all users
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();

    }

    /**
     * Create a new user.
     *
     * @param user The new user that should be added
     * @return The newly created user
     */
    @PostMapping("/users")
    public User createNewActivity(@Valid @RequestBody User user) {

        return userRepository.save(user);
    }

    /**
     * Listens for postrequests in users/login.
     * Sends http response with status and message
     *
     * @param logUser Receives a User from post request
     * @return BasicHttpResponse
     */
    @PostMapping("/api/auth/login")
    public BasicHttpResponse loginPost(@Valid @RequestBody User logUser,
                                       HttpServletResponse response) {

        String message;
        String status;

        User user = userRepository.findUserByUsername(logUser.getUsername());

        if (user == null) {
            message = "No user found with this combination";
            status = "fail";
        } else {
            if (!(passwordEncoder.matches(logUser.getPassword(), user.getPassword()))) {
                message = "No user found with this combination";
                status = "fail";
            } else {
                message = "Valid combination";
                status = "success";

                Long now = System.currentTimeMillis();

                String token = Jwts.builder()
                        .setSubject(user.getUsername())
                        .setId(user.getId().toString())
                        .setIssuedAt(new Date(now))
                        .setExpiration(new Date(now + JwtConfig.EXPIRATION_TIME * 1000))
                        .signWith(SignatureAlgorithm.HS512,
                                JwtConfig.SECRET.getBytes())
                        .compact();

                response.addHeader("Authorization", "Bearer " + token);

                System.out.println(token);

            }
        }

        return new BasicHttpResponse(status, message);
    }

    /**
     * Listens for post request on users/register.
     * Adds user to database.
     *
     * @param regUser Receives User from post request
     * @return BasicHttpResponse
     */
    @PostMapping("/api/auth/register")
    public BasicHttpResponse addUser(@Valid @RequestBody User regUser) {

        String hashedPassword = passwordEncoder.encode(regUser.getPassword());

        regUser.setPassword(hashedPassword);

        User user = userRepository.findUserByUsername(regUser.getUsername());

        if (user == null) {
            userRepository.save(regUser);
            return new BasicHttpResponse("success", "User registered");
        } else {
            return new BasicHttpResponse("fail", "Username already exists");
        }
    }

    /**
     * gets list of achievements from server for particular user.
     *
     * @param auth authenticarion parameter
     * @return the list of activities which are linked to a specific user.
     */
    @GetMapping("/api/users/achievements")
    public ResponseEntity<List<UserAchievement>> getListOfAchievements(Authentication auth) {

        User loggedInUser = (User) auth.getCredentials();


        return userRepository.findById(loggedInUser.getId()).map(user -> {
            return new ResponseEntity<>(userAchievementRepository.findAllByUser(user),
                    HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    /**
     * Returns the list of activities which are linked to a specific user.
     *
     * @return The list of activities for that user
     */
    @GetMapping("/api/users/activities")
    public ResponseEntity<List<UserActivity>> getListOfActivities(Authentication authentication) {

        User loggedInUser = (User) authentication.getCredentials();


        return userRepository.findById(loggedInUser.getId()).map(user -> {
            return new ResponseEntity<>(userActivityRepository.findAllByUser(user),
                    HttpStatus.OK);

        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Links a user to an achievement.
     *
     * @param req            request
     * @param authentication authentication
     * @return returns the user
     */
    @PostMapping("/api/users/achievements")
    @Transactional
    public ResponseEntity<User> linkuserToAchievement(
            @RequestBody @Valid UserAchievementRequest req,
            Authentication authentication) {

        User loggedInUser = (User) authentication.getCredentials();


        return userRepository.findById(loggedInUser.getId()).map(user -> {
            return achievementRepository.findById(req.getAchievementId()).map(achievement -> {


                UserAchievement userAchievement = new UserAchievement();
                userAchievement.setUser(user);
                userAchievement.setAchievement(achievement);
                userAchievement.setAchievementName(req.getAchievementName());
                userAchievementRepository.save(userAchievement);


                return new ResponseEntity<>(user, HttpStatus.OK);
            }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


    /**
     * Link a user to a certain activity.
     *
     * @return The given user data
     */
    @PostMapping("/api/users/activities")
    @Transactional
    public ResponseEntity<User> linkUserToActivity(
            @RequestBody @Valid UserActivityRequest req,
            Authentication authentication) {

        User loggedInUser = (User) authentication.getCredentials();

        return userRepository.findById(loggedInUser.getId()).map(user -> {

            return activityRepository.findById(req.getActivityId()).map(activity -> {

                UserActivity userActivityy;

                switch (activity.getCategory()) {

                    case Home:
                        userActivityy = new UserActivityHouse();
                        ((UserActivityHouse)
                                userActivityy).setDegreesBefore(req.getDegreesBefore());
                        ((UserActivityHouse)
                                userActivityy).setDegreesAfter(req.getDegreesAfter());
                        break;

                    case Transport:
                        userActivityy = new UserActivityTravel();
                        ((UserActivityTravel) userActivityy).setDistance(req.getDistance());
                        break;

                    case Meal:
                        userActivityy = new UserActivityMeal();
                        ((UserActivityMeal)
                                userActivityy).setVeganCoefficient(req.getVeganCoefficient());
                        break;
                    case Insulation:
                        userActivityy = new UserActivityInsulation();
                        ((UserActivityInsulation)
                                userActivityy).setAreaInsulated(req.getAreaInsulated());
                        break;
                    case SolarPanels:
                        userActivityy = new UserActivitySolarPanels();
                        ((UserActivitySolarPanels)
                                userActivityy).setEnergySavedSolar(req.getEnergySavedSolar());
                        break;
                    case LocalProduction:
                        userActivityy = new UserActivityLocalProduction();
                        ((UserActivityLocalProduction)
                                userActivityy).setMassBought(req.getMassBought());
                        break;
                    default:
                        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);

                }

                userActivityy.setUser(user);
                userActivityy.setActivity(activity);
                userActivityy.setType(ActivityType.Habit);
                userActivityy.setPoints(userActivityy.calculatePoints());
                userActivityRepository.save(userActivityy);

                user.setPoints(user.getPoints() + userActivityy.getPoints());
                userRepository.save(user);

                return new ResponseEntity<>(user, HttpStatus.OK);

            }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes all records in UserAchievement database.
     *
     * @return status
     */
    @DeleteMapping("/api/users/achievements")
    @Transactional
    public ResponseEntity<Object> deleteConnectedAchievements() {

        userAchievementRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete a connection between a user and an activity.
     *
     * @param activityId The id of the activity to unlink
     * @return Void
     */
    @DeleteMapping("/api/users/activities/{activity_id}")
    @Transactional
    public ResponseEntity<Object> deleteConnectedActivity(
            Authentication authentication,
            @PathVariable(value = "activity_id") long activityId
    ) {

        User loggedInUser = (User) authentication.getCredentials();


        return userActivityRepository.findById(activityId).map(userActivity -> {

            if (userActivity.getUser().getId() == loggedInUser.getId()) {

                userActivity.getUser().setPoints(userActivity.getUser().getPoints()
                        - userActivity.getPoints());
                userRepository.save(userActivity.getUser());

                userActivityRepository.deleteById(activityId);

                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    /**
     * Get user by Token.
     *
     * @return Found user (Status 200) or Not Found (Status 400)
     */
    @GetMapping("/api/users/info")
    public ResponseEntity<User> getUserById(Authentication authentication) {
        User loggedInUser = (User) authentication.getCredentials();

        return userRepository.findById(loggedInUser.getId()).map(user -> {
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update a User.
     *
     * @param newUser The user with the new parameters
     * @return The updated user (Status 200) or status 404 if the given user id was not found
     */
    @PutMapping("/api/users/info")
    public ResponseEntity<User> updateUser(Authentication authentication,
                                           @RequestBody User newUser) {

        User loggedInUser = (User) authentication.getCredentials();

        return userRepository.findById(loggedInUser.getId()).map(user -> {

            user.setEmail(newUser.getEmail());
            user.setUsername(newUser.getUsername());
            if (newUser.getPassword() == null) {
                user.setDailyGoal(newUser.getDailyGoal());
                user.setWeeklyGoal(newUser.getWeeklyGoal());
                user.setMonthlyGoal(newUser.getMonthlyGoal());
            } else {
                String hashedPassword = passwordEncoder.encode(newUser.getPassword());
                user.setPassword(hashedPassword);
            }
            user.setPoints(newUser.getPoints());

            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Delete user.
     *
     * @param id The id of the user that will be deleted
     * @return status 200 if the user was deleted or 404 if the user was not found
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") long id) {

        return userRepository.findById(id).map(user -> {

            userRepository.delete(user);

            return new ResponseEntity(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}
