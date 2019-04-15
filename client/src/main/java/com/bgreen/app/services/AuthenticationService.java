package com.bgreen.app.services;

import com.google.gson.Gson;

import com.bgreen.app.auth.AuthUser;
import com.bgreen.app.models.HttpResponse;
import com.bgreen.app.models.User;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class AuthenticationService extends AbstractService {
    private Gson gson = new Gson();

    /**
     * authentication.
     * @param user User to authenticate
     * @param uri url
     * @return true if authentication was successful
     */
    public boolean authenticate(User user, String uri) {
        ResponseEntity<String> response = this.getRestTemplate().exchange(baseUrl + uri,
                HttpMethod.POST, this.getHeaders(user), String.class);
        HttpHeaders headers = response.getHeaders();
        if (headers.get("Authorization") != null) {
            AuthUser.setToken(headers.get("Authorization").get(0));
        }
        System.out.println(response.getBody());
        HttpResponse httpResponse =
                gson.fromJson(response.getBody(), HttpResponse.class);
        return !httpResponse.getStatus().equals("fail");
    }
}
