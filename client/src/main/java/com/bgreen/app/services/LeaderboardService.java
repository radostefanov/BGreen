package com.bgreen.app.services;

import com.bgreen.app.enums.FriendshipStatus;
import com.bgreen.app.models.User;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderboardService extends AbstractService {

    /**
     * Get the top 5 users.
     *
     * @return a list with the top 5 users ordered by points.
     */
    public User[] getTop5Users() {

        ResponseEntity<User[]> top5Users = this.getRestTemplate()
                .exchange(baseUrl + "api/leaderboards/global",
                HttpMethod.GET, this.getHeaders(), User[].class);

        List<User> list = Arrays.asList(top5Users.getBody());



        for (User user : list) {

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            String test = (df.format(user.getPoints()));
            user.setPoints(Double.parseDouble((df.format(user.getPoints())).replace(",", "")));
        }


        return list.toArray(new User[list.size()]);
    }

    /**
     * Get the top 5 users.
     *
     * @return a list with the top 5 users ordered by points.
     */
    public User[] getTop5Friends() {

        ResponseEntity<User[]> top5Users = this.getRestTemplate()
                .exchange(baseUrl + "api/friends/user",
                HttpMethod.GET, this.getHeaders(), User[].class);
        ArrayList<User> users = new ArrayList<>(Arrays.asList(top5Users.getBody()));

        List<User> listOfUsers = users.stream().filter(u -> u.getFriendshipStatus()
                .equals(FriendshipStatus.ACCEPTED)).collect(Collectors.toList());

        for (User user : listOfUsers) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            user.setPoints(Double.parseDouble((df.format(user.getPoints())).replace(",", "")));
        }

        return listOfUsers.toArray(new User[listOfUsers.size()]);

    }
}
