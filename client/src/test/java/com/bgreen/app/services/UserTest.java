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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    private final String baseUrl = App.baseUrl;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUserInfoTest() {
        User user = new User("username", "password", "email", 0);
        ResponseEntity<User> toRespond = new ResponseEntity<>(
               user, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/users/info"),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<User>> any(),
                Matchers.<Class<User>> any()
                )
        ).thenReturn(toRespond);

        User response = userService.getUserInfo();

        System.out.println("Username: " + response.getUsername());
        System.out.println("Password: " + response.getPassword());
        System.out.println("Email: " + response.getEmail());
        System.out.println("Points: " + response.getPoints());

        assertEquals("username", response.getUsername());
        assertEquals("password", response.getPassword());
        assertEquals("email", response.getEmail());
        assertEquals(0.0, response.getPoints(), 0.2);
    }

    @Test
    public void modifyUserInfoTest() {
        User user = new User("username", "password", "email", 0);
        ResponseEntity<User> toRespond = new ResponseEntity<>(
                user, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/users/info"),
                Matchers.eq(HttpMethod.PUT),
                Matchers.<HttpEntity<User>>any(),
                Matchers.<Class<User>>any()
                )
        ).thenReturn(toRespond);

        User response = userService.modifyUserInfo(user);

        assertEquals(user.getUsername(), response.getUsername());
        System.out.println("Success username");

        assertEquals(user.getPassword(), response.getPassword());
        System.out.println("Success password");

        assertEquals(user.getEmail(), response.getEmail());
        System.out.println("Success email");

        assertEquals(user.getPoints(), response.getPoints());
        System.out.println("Success points");
    }
}
