package com.bgreen.app.controllers;

import com.bgreen.app.models.Activity;
import com.bgreen.app.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.validation.Valid;


@RestController
public class ActivityController {

    /**
     * Inject the activity repository for controlling all data for activities.
     */
    @Autowired
    private ActivityRepository activityRepository;

    /**
     * List all activities.
     * @return Array of all activities in the database
     */
    @GetMapping("/api/activities")
    public List<Activity> getAllActivities(Authentication authentication) {

        return activityRepository.findAll();
    }

    /**
     * Create a new activity.
     * @param activity -> The new activity passed from the user
     * @return The newly created activity with all parameters
     */
    @PostMapping(value = "/api/activities", consumes = {"application/json"})
    public Activity createNewActivity(@Valid @RequestBody Activity activity) {

        return activityRepository.save(activity);
    }

    /**
     * Get specific activity by given ID.
     * @param id The id of the needed activity
     * @return The found activity (Status 200) or Not Found (Status 404)
     */
    @GetMapping("/api/activities/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable(value = "id") long id) {

        return activityRepository.findById(id).map(e -> ResponseEntity.ok(e))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * Update an activity.
     * @param id The id of the activity that will be updated
     * @param newActivity The activity with the new parameters
     * @return The updated activity (Status 200) or status 404 if the given activity was not found
     */
    @PutMapping("/api/activities/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable(value = "id") long id,
                                                   @RequestBody Activity newActivity) {

        return activityRepository.findById(id).map(activity -> {

            activity.setCo2Interval(newActivity.getCo2Interval());
            activity.setCo2Points(newActivity.getCo2Points());
            activity.setName(newActivity.getName());


            return new ResponseEntity<>(activityRepository.save(activity), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Delete activity.
     * @param id The id of the activity that will be deleted
     * @return status 200 if the activity was deleted or 404 if the activity was not found
     */
    @DeleteMapping("/api/activities/{id}")
    public ResponseEntity deleteActivity(@PathVariable(value = "id") long id) {

        return activityRepository.findById(id).map(activity -> {

            activityRepository.delete(activity);

            return new ResponseEntity(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}
