package com.bgreen.app.services;

import com.bgreen.app.models.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class UserService extends com.bgreen.app.services.AbstractService {

    /**
     * modifies user info.
     * @param user User
     * @return modified user
     */
    public User modifyUserInfo(User user) {
        ResponseEntity<User> response = this.getRestTemplate().exchange(baseUrl + "api/users/info",
                HttpMethod.PUT, this.getHeaders(user), User.class);
        return response.getBody();
    }

    /**
     * gets user info.
     * @return user
     */
    public User getUserInfo() {
        ResponseEntity<User> user = this.getRestTemplate()
                .exchange(baseUrl + "api/users/info", HttpMethod.GET,
                        this.getHeaders(), User.class);
        return user.getBody();
    }
}
