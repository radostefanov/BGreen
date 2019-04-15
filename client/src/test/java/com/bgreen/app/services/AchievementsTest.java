package com.bgreen.app.services;

import com.bgreen.app.App;
import com.bgreen.app.models.Achievement;
import com.bgreen.app.models.User;
import com.bgreen.app.requestmodels.UserAchievementRequest;
import com.bgreen.app.services.AchievementService;
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
public class AchievementsTest {

    private final String baseUrl = App.baseUrl;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AchievementService achievementService;

    @Test
    public void getAchievementsTest(){

        Achievement achievement = new Achievement(Long.valueOf(1), "name", 10.0);
        Achievement achievement1 = new Achievement(Long.valueOf(2), "name2", 20.0);

        Achievement[] array = {achievement, achievement1};

        ResponseEntity<Achievement[]> toRespond = new ResponseEntity<>(array, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/users/achievements"),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<Achievement[]>> any(),
                Matchers.<Class<Achievement[]>> any()
        )
        ).thenReturn(toRespond);

        Achievement[] result = achievementService.getAchievements("api/users/achievements");

        assertEquals(result.length, 2);
    }

    @Test
    public void linkAchivementsToUserTest() {

        User user = new User("name", "pwd", "user@gmail.com", 10);

        UserAchievementRequest request = new UserAchievementRequest();

        request.setAchievementName("name");
        request.setAchievementId(Long.valueOf(1));

        ResponseEntity<User> toRespond = new ResponseEntity<>(user, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/users/achievements"),
                Matchers.eq(HttpMethod.POST),
                Matchers.<HttpEntity<UserAchievementRequest>> any(),
                Matchers.<Class<User>> any()
                )
        ).thenReturn(toRespond);

        User result = achievementService.linkAchievementsToUser(request);

        assertEquals(result, user);
    }

}
