package com.bgreen.app.services;

import com.bgreen.app.models.UserActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class StatisticsService extends AbstractService {

    /**
     * gets daily points for a user.
     * @param dailyMap Map with points
     * @param userActivities array of userActivities
     * @param currentCal for testing purposes, let's data object be entered.
     * @throws ParseException when date can't be parsed
     */
    public void getDailyPoints(HashMap<String, Double> dailyMap,
                               UserActivity[] userActivities,
                               Calendar currentCal) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        currentCal.setTime(dateFormat.parse(dateFormat.format(new Date())));
        String[] dayList = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (String i: dayList) {
            dailyMap.put(i, 0.0);
        }

        for (UserActivity userActivity : userActivities) {
            Calendar activityCal = Calendar.getInstance();

            activityCal.setTime(dateFormat.parse(userActivity.getCreatedAt()));
            String date = dateFormat.parse(userActivity.getCreatedAt()).toString();

            if (activityCal.get(Calendar.WEEK_OF_YEAR)
                    != currentCal.get(Calendar.WEEK_OF_YEAR)) {
                continue;
            }

            dailyMap.put(date.substring(0, 3), dailyMap.get(date.substring(0, 3))
                    + userActivity.getPoints());
        }
    }

    /**
     * gets weekly points for a user.
     * @param weeklyMap Map with points
     * @param userActivities array of userActivities
     * @param currentCal for testing purposes, let's data object be entered.
     * @throws ParseException when date can't be parsed
     */
    public void getWeeklyPoints(HashMap<Integer, Double> weeklyMap,
                                UserActivity[] userActivities,
                                Calendar currentCal) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        currentCal.setTime(dateFormat.parse(dateFormat.format(new Date())));
        for (int i = 1; i < 6; i++) {
            weeklyMap.put(i, 0.0);
        }

        for (UserActivity userActivity : userActivities) {
            String activityDate = userActivity.getCreatedAt();

            Calendar activityCal = Calendar.getInstance();
            activityCal.setTime(dateFormat.parse(activityDate));

            if (activityCal.get(Calendar.MONTH)
                    != currentCal.get(Calendar.MONTH)) {
                continue;
            }

            int weekOfMonth = activityCal.get(Calendar.WEEK_OF_MONTH);
            weeklyMap.put(weekOfMonth, weeklyMap.get(weekOfMonth) + userActivity.getPoints());
        }
    }

    /**
     * gets monthly points for a user.
     * @param monthlyMap Map with points
     * @param userActivities array of userActivities
     * @param currentCal for testing purposes, let's data object be entered.
     * @throws ParseException when date can't be parsed
     */
    public void getMonthlyPoints(HashMap<String, Double> monthlyMap,
                                 UserActivity[] userActivities,
                                 Calendar currentCal) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        currentCal.setTime(dateFormat.parse(dateFormat.format(new Date())));
        String[] monthList = {"j", "f", "m", "a", " m", " j", "  j", " a", "s", "o", "n", "d"};
        for (String i: monthList) {
            monthlyMap.put(i, 0.0);
        }

        for (UserActivity userActivity : userActivities) {
            String activityDate = userActivity.getCreatedAt();

            Calendar activityCal = Calendar.getInstance();
            activityCal.setTime(dateFormat.parse(activityDate));
            if (activityCal.get(Calendar.YEAR)
                    != currentCal.get(Calendar.YEAR)) {
                continue;
            }

            double points = userActivity.getPoints();

            String tempMonthName = dateFormat.parse(activityDate).toString()
                    .substring(4, 7).toLowerCase().substring(0, 1);

            monthlyMap.put(tempMonthName, monthlyMap.get(tempMonthName) + points);
        }
    }
}
