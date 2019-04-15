package com.bgreen.app.services;

import com.bgreen.app.App;
import com.bgreen.app.models.UserActivity;
import org.junit.Before;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsTest {

    private HashMap<String, Double> monthlyMap = new HashMap<>();

    private HashMap<Integer, Double> weeklyMap = new HashMap<>();

    private HashMap<String, Double> dailyMap = new HashMap<>();

    private UserActivity userActivity1 = new UserActivity();

    private UserActivity userActivity2 = new UserActivity();

    private UserActivity userActivity3 = new UserActivity();

    private UserActivity userActivity4 = new UserActivity();

    private UserActivity userActivity5 = new UserActivity();

    private final String baseUrl = App.baseUrl;

    private Calendar currentCal = Calendar.getInstance();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ActivityService activityService;

    @Before
    public void setUp() throws ParseException {
        userActivity1.setCreatedAt("2019-04-09T11:52:00.503+0000");
        userActivity2.setCreatedAt("2019-03-23T11:52:00.503+0000");
        userActivity3.setCreatedAt("2017-04-09T11:52:00.503+0000");
        userActivity4.setCreatedAt("2018-04-12T11:52:00.503+0000");
        userActivity5.setCreatedAt("2019-04-10T11:52:00.503+0000");
        Date date = new GregorianCalendar(2019, Calendar.APRIL, 4).getTime();
        currentCal.setTime(dateFormat.parse(dateFormat.format(date)));
    }

    @Test
    public void getDailyPointsTest() throws ParseException {
        UserActivity[] userActivities = {userActivity1, userActivity2, userActivity3,
                userActivity3, userActivity4, userActivity5};
        ResponseEntity<UserActivity[]> toRespond = new ResponseEntity<>(userActivities, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/users/activities"),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<UserActivity[]>> any(),
                Matchers.<Class<UserActivity[]>> any()
                )
        ).thenReturn(toRespond);

        userActivities = activityService.getUserActivities();

        new StatisticsService().getDailyPoints(dailyMap, userActivities, currentCal);

        assertNotNull(dailyMap);

        assert(!dailyMap.isEmpty());

        assert(weeklyMap.isEmpty());

        assert(monthlyMap.isEmpty());

        assert(dailyMap.containsKey("Mon"));

        assertEquals(7, dailyMap.entrySet().size());
    }

    @Test
    public void getWeeklyPoints() throws ParseException {
        UserActivity[] userActivities = {userActivity1, userActivity2, userActivity3,
                userActivity3, userActivity4, userActivity5};
        ResponseEntity<UserActivity[]> toRespond = new ResponseEntity<>(userActivities, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/users/activities"),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<UserActivity[]>> any(),
                Matchers.<Class<UserActivity[]>> any()
                )
        ).thenReturn(toRespond);

        userActivities = activityService.getUserActivities();

        new StatisticsService().getWeeklyPoints(weeklyMap, userActivities, currentCal);

        assertNotNull(weeklyMap);

        assert(dailyMap.isEmpty());

        assert(!weeklyMap.isEmpty());

        assert(monthlyMap.isEmpty());

        assert(weeklyMap.containsKey(2));

        assertEquals(5, weeklyMap.entrySet().size());
    }

    @Test
    public void getMonthlyPoints() throws ParseException {
        UserActivity[] userActivities = {userActivity1, userActivity2, userActivity3,
                userActivity3, userActivity4, userActivity5};
        ResponseEntity<UserActivity[]> toRespond = new ResponseEntity<>(userActivities, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.eq(baseUrl + "api/users/activities"),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<UserActivity[]>> any(),
                Matchers.<Class<UserActivity[]>> any()
                )
        ).thenReturn(toRespond);

        userActivities = activityService.getUserActivities();

        new StatisticsService().getMonthlyPoints(monthlyMap, userActivities, currentCal);

        assertNotNull(monthlyMap);

        assert(dailyMap.isEmpty());

        assert(weeklyMap.isEmpty());

        assert(!monthlyMap.isEmpty());

        assertEquals(12, monthlyMap.entrySet().size());
    }
}
