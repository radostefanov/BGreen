package com.bgreen.app.services;

import com.bgreen.app.App;
import com.bgreen.app.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationTest {

    private final String baseUrl = App.baseUrl;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void loginTest() {
        ResponseEntity<String> toRespond = new ResponseEntity<>(
                "{\"status\":\"success\",\"message\":\"Valid combination\"}", HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/auth/login"),
                Matchers.eq(HttpMethod.POST),
                Matchers.<HttpEntity<User>> any(),
                Matchers.<Class<String>> any()
                )
        ).thenReturn(toRespond);

        boolean response = authenticationService.authenticate(new User("username","password"),
                "api/auth/login");

        System.out.println("Response: " + response);

        assert(response);
    }

    @Test
    public void loginFailTest() {
        ResponseEntity<String> toRespond = new ResponseEntity<>(
                "{\"status\":\"fail\",\"message\":\"No user found with this combination\"}", HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/auth/login"),
                Matchers.eq(HttpMethod.POST),
                Matchers.<HttpEntity<User>> any(),
                Matchers.<Class<String>> any()
                )
        ).thenReturn(toRespond);

        boolean response = authenticationService.authenticate(new User("username","password"),
                "api/auth/login");

        System.out.println("Response: " + response);

        assert(!response);
    }

    @Test
    public void registerTest() {
        ResponseEntity<String> toRespond = new ResponseEntity<>(
                "{\"status\":\"success\",\"message\":\"User registered\"}", HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/auth/register"),
                Matchers.eq(HttpMethod.POST),
                Matchers.<HttpEntity<User>> any(),
                Matchers.<Class<String>> any()
                )
        ).thenReturn(toRespond);

        boolean response = authenticationService.authenticate(new User("user","pass"),
                "api/auth/register");

        System.out.println("Response: " + response);

        assert(response);
    }

    @Test
    public void registerFailTest() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "randomGarbage");

        ResponseEntity<String> toRespond = new ResponseEntity<>(
                "{\"status\":\"fail\",\"message\":\"Username already exists\"}", httpHeaders, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/auth/register"),
                Matchers.eq(HttpMethod.POST),
                Matchers.<HttpEntity<User>> any(),
                Matchers.<Class<String>> any()
                )
        ).thenReturn(toRespond);

        boolean response = authenticationService.authenticate(new User("user","pass"),
                "api/auth/register");

        System.out.println("Response: " + response);

        assert(!response);
    }
}