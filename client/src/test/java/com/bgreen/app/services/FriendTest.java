package com.bgreen.app.services;

import com.bgreen.app.App;
import com.bgreen.app.models.Friend;
import com.bgreen.app.models.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static junit.framework.TestCase.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class FriendTest {

    protected String baseUrl = App.baseUrl;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FriendService friendService;

    @Test
    public void getFriends_test() {

        User user1 = new User("user1", "password");

        User[] userArray = {
                user1
        };

        ResponseEntity<User[]> toRespond = new ResponseEntity<>(userArray, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/friends/user"),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<User[]>> any(),
                Matchers.<Class<User[]>> any()
                )
        ).thenReturn(toRespond);

        List<User> users = friendService.getFriends();

        assertEquals(users.get(0), user1);
        assertEquals(users.size(), 1);
    }

    @Test
    public void addFriend_test() {

        User user1 = new User("user1", "password");
        User user2 = new User("user2", "password2");
        Friend friend = new Friend(user1, user2);

        ResponseEntity<Friend> toRespond = new ResponseEntity<>(friend, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/friends"),
                Matchers.eq(HttpMethod.POST),
                Matchers.<HttpEntity<Friend>> any(),
                Matchers.<Class<Friend>> any()
                )
        ).thenReturn(toRespond);

        Friend responseFriend = friendService.addFriend(user2.getUsername());

        assertEquals(responseFriend.getUser1(), user1);
        assertEquals(responseFriend.getUser2(), user2);

    }

    @Test
    public void deleteFriend_test() {

        User user1 = new User("user1", "password");
        User user2 = new User("user2", "password2");
        Friend friend = new Friend(user1, user2);

        ResponseEntity<Friend> toRespond = new ResponseEntity<>(friend, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/friends/user"),
                Matchers.eq(HttpMethod.POST),
                Matchers.<HttpEntity<Friend>> any(),
                Matchers.<Class<Friend>> any()
                )
        ).thenReturn(toRespond);

        Friend responseFriend = friendService.deleteFriend(user2.getUsername());

        assertEquals(responseFriend.getUser1(), user1);
        assertEquals(responseFriend.getUser2(), user2);
    }

    @Test
    public void acceptRequest_test() {

        User user1 = new User("user1", "password");
        User user2 = new User("user2", "password2");
        Friend friend = new Friend(user1, user2);

        ResponseEntity<Friend> toRespond = new ResponseEntity<>(friend, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/friends/user/accept"),
                Matchers.eq(HttpMethod.POST),
                Matchers.<HttpEntity<Friend>> any(),
                Matchers.<Class<Friend>> any()
                )
        ).thenReturn(toRespond);

        Friend responseFriend = friendService.acceptRequest(user2.getUsername());

        assertEquals(responseFriend.getUser1(), user1);
        assertEquals(responseFriend.getUser2(), user2);
    }

}