package com.bgreen.app.services;

import com.bgreen.app.models.Achievement;
import com.bgreen.app.models.User;
import com.bgreen.app.requestmodels.UserAchievementRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class AchievementService extends AbstractService {

    /**
     * gets all achievements from database.
     * @param url url
     * @return list of achievements
     */
    public Achievement[] getAchievements(String url) {

        ResponseEntity<Achievement[]> achievements = this.getRestTemplate().exchange(baseUrl + url,
                HttpMethod.GET, this.getHeaders(), Achievement[].class);
        return achievements.getBody();
    }

    /**
     * links achievements to the user.
     * @param userAchievementRequest request
     * @return the user
     */
    public User linkAchievementsToUser(UserAchievementRequest userAchievementRequest) {
        ResponseEntity<User> response = this.getRestTemplate().exchange(baseUrl
                + "api/users/achievements", HttpMethod.POST,
                this.getHeaders(userAchievementRequest), User.class);
        System.out.println(response.getBody());
        return response.getBody();
    }

    /**
     * deletes all achievements in the database.
     */
    public void deleteAchievements() {
        this.getRestTemplate()
                .exchange(baseUrl + "api/users/achievements/" , HttpMethod.DELETE,
                        this.getHeaders(), String.class);
    }
}
