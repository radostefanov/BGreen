package com.bgreen.app.services;


import com.bgreen.app.App;
import com.bgreen.app.enums.FriendshipStatus;
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
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class LeaderboardsTest {

    protected final String baseUrl = App.baseUrl;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LeaderboardService leaderboardService;

    @Test
    public void leaderboardsTest(){

        User user1 = new User("username1", "pwd", "email1@email.nl", 10);
        User user2 = new User("username2", "pwd", "email2@email.nl", 20);
        User user3 = new User("username3", "pwd", "email3@email.nl", 30);
        User user4 = new User("username4", "pwd", "email4@email.nl", 40);
        User user5 = new User("username5", "pwd", "email5@email.nl", 5);
        User user6 = new User("username6", "pwd", "email6@email.nl", 60);

        User[] array = {
                user6, user4, user3, user2, user1
        };

        ResponseEntity<User[]> toRespond = new ResponseEntity<>(array, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/leaderboards/global"),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<User[]>> any(),
                Matchers.<Class<User[]>> any()
                )
        ).thenReturn(toRespond);

        User[] result = leaderboardService.getTop5Users();

        assertEquals(result.length, 5);

        assertTrue(true);
    }

    @Test
    public void leaderboardsTop5FriendsTest(){

        User user1 = new User("username1", "pwd", "email1@email.nl", 10);
        User user2 = new User("username2", "pwd", "email2@email.nl", 20);
        User user3 = new User("username3", "pwd", "email3@email.nl", 30);
        User user4 = new User("username4", "pwd", "email4@email.nl", 40);
        User user5 = new User("username5", "pwd", "email5@email.nl", 5);
        User user6 = new User("username6", "pwd", "email6@email.nl", 60);

        user1.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        user2.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        user3.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        user4.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        user5.setFriendshipStatus(FriendshipStatus.ACCEPTED);
        user6.setFriendshipStatus(FriendshipStatus.WAITING_FOR_ANSWER);




        User[] array = {
                user6, user4, user3, user2, user1
        };

        ResponseEntity<User[]> toRespond = new ResponseEntity<>(array, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/friends/user"),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<User[]>> any(),
                Matchers.<Class<User[]>> any()
                )
        ).thenReturn(toRespond);

        User[] result = leaderboardService.getTop5Friends();

        assertEquals(result.length, 4);

        assertTrue(true);
    }
}
