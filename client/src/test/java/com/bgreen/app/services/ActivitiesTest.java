package com.bgreen.app.services;

import com.bgreen.app.App;
import com.bgreen.app.auth.AuthUser;
import com.bgreen.app.models.Activity;

import static org.junit.Assert.assertNull;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ActivitiesTest {

    protected final String baseUrl = App.baseUrl;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ActivityService activityService;

    @Test
    public void activities_test(){

        Activity activity1 = new Activity(1, "cr_at","upd_at","name",34, 23);

        Activity[] arr = {
                activity1
        };

        ResponseEntity<Activity[]> toRespond = new ResponseEntity<Activity[]>(arr, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/activities"),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<Activity[]>> any(),
                Matchers.<Class<Activity[]>> any()
                )
        ).thenReturn(toRespond);

        Activity[] activities = activityService.getAll();

        assertEquals(activities.length, 1);

        assertTrue(true);
    }

    @Test
    public void getHeaders() {
        AuthUser.setToken(null);
        ActivityService service = new ActivityService();
        HttpEntity<String> test = service.getHeaders();
        HttpHeaders headers = test.getHeaders();

        assertNull(headers.get("Authorization"));
    }
}
