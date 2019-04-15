package com.bgreen.app.controllers;

import com.bgreen.app.models.Achievement;
import com.bgreen.app.repositories.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class AchievementController {

    /**
     * Inject the achievement repository for controlling all data for achievements.
     */
    @Autowired
    private AchievementRepository achievementRepository;

    /**
     * List all achievements.
     *
     * @return Array of all achievements in the database
     */
    @GetMapping("/api/achievements")
    public List<Achievement> getAllAchievements() {

        return achievementRepository.findAll();
    }

    /**
     * Create a new achievement.
     *
     * @param achievement -> The new achievement passed from the admin user
     * @return The newly created achievement with all parameters
     */
    @PostMapping("/api/achievements")
    public Achievement createNewAchievement(@Valid @RequestBody Achievement achievement) {

        return achievementRepository.save(achievement);
    }

    /**
     * Get specific achievement by given ID.
     *
     * @param id The id of the needed achievement
     * @return The found achievement (Status 200) or Not Found (Status 404)
     */
    @GetMapping("/api/achievements/{id}")
    public ResponseEntity<Achievement> getAchievementById(@PathVariable(value = "id") long id) {

        return achievementRepository.findById(id).map(e -> ResponseEntity.ok(e))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * Update an achievement.
     *
     * @param id             The id of the achievement that will be updated
     * @param newAchievement The achievement with the new parameters
     * @return The updated achievement (Status 200) or status 404 if
     *     the given achievement was not found.
     */
    @PutMapping("/api/achievements/{id}")
    public ResponseEntity<Achievement> updateAchievement(@PathVariable(value = "id") long id,
                                                         @RequestBody Achievement newAchievement) {

        return achievementRepository.findById(id).map(achievement -> {

            achievement.setAchievementName(newAchievement.getAchievementName());
            achievement.setRequirements(newAchievement.getRequirements());
            achievement.setCreatedAt(newAchievement.getCreatedAt());
            achievement.setUpdatedAt(newAchievement.getUpdatedAt());


            return new ResponseEntity<>(achievementRepository.save(achievement), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Delete achievement.
     *
     * @param id The id of the achievement that will be deleted
     * @return status 200 if the achievement was deleted or 404 if the achievement was not found
     */
    @DeleteMapping("/api/achievements/{id}")
    public ResponseEntity deleteAchievement(@PathVariable(value = "id") long id) {

        return achievementRepository.findById(id).map(achievement -> {

            achievementRepository.delete(achievement);

            return new ResponseEntity(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

}
