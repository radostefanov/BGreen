package com.bgreen.app.services;

import com.bgreen.app.App;
import com.bgreen.app.auth.AuthUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AbstractService {
    protected final String baseUrl = App.baseUrl;

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    protected HttpEntity<String> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        if (AuthUser.getToken() != null) {
            headers.add("Authorization", AuthUser.getToken());
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        return entity;
    }

    protected HttpEntity<?> getHeaders(Object parameters) {


        HttpHeaders headers = new HttpHeaders();
        if (AuthUser.getToken() != null) {
            headers.add("Authorization", AuthUser.getToken());
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(parameters, headers);

        return entity;
    }
}
