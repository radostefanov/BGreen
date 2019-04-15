package com.bgreen.app.services;

import com.bgreen.app.auth.AuthUser;
import com.bgreen.app.models.Activity;
import com.bgreen.app.models.User;
import com.bgreen.app.models.UserActivity;
import com.bgreen.app.requestmodels.UserActivityRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ActivityService extends AbstractService {


    /**
     * gets all activities from the server.
     *
     * @return array of all activities
     */
    public Activity[] getAll() {
        ResponseEntity<Activity[]> activities = this.getRestTemplate()
                .exchange(baseUrl + "api/activities", HttpMethod.GET,
                        this.getHeaders(), Activity[].class);

        return activities.getBody();
    }

    /**
     * links an activity to a user.
     * @param userActivityRequest request to link user and activity
     */
    public void linkActivityToUser(UserActivityRequest userActivityRequest) {
        ResponseEntity<User> response = this.getRestTemplate()
                .exchange(baseUrl + "api/users/activities", HttpMethod.POST,
                        this.getHeaders(userActivityRequest), User.class);

        System.out.println(response.getBody());

        AuthUser.setPoints(response.getBody().getPoints());
    }

    /**
     * gets all userActivities from the server (for a user).
     * @return array of activities
     */
    public UserActivity[] getUserActivities() {

        ResponseEntity<UserActivity[]> activities = this.getRestTemplate()
                .exchange(baseUrl + "api/users/activities", HttpMethod.GET,
                        this.getHeaders(), UserActivity[].class);

        ArrayList<UserActivity> allActivities = new ArrayList<>(Arrays.asList(activities.getBody()));
        Collections.reverse(allActivities);

        return allActivities.toArray(new UserActivity[allActivities.size()]);
    }


    /**
     * removes a connection between the user and a certain activity.
     */
    public void deleteActivity(Long connectionId) {

        this.getRestTemplate()
                .exchange(baseUrl + "api/users/activities/" + connectionId, HttpMethod.DELETE,
                        this.getHeaders(), String.class);

    }
}
