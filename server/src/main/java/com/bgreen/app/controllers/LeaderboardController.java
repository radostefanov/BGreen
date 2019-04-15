package com.bgreen.app.controllers;

import com.bgreen.app.models.User;
import com.bgreen.app.repositories.UserRepository;
import com.bgreen.app.reqmodels.UserFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LeaderboardController {

    @Autowired
    UserRepository userRepository;

    /**
     * Waits for requests to this url.
     * @return Returns the top 5 users by points.
     */
    @GetMapping("api/leaderboards/global")
    public List<UserFriend> getTop5GlobalUsers() {

        List<User> users =  userRepository.findTop5ByOrderByPointsDesc();

        List<UserFriend> userFriends = new ArrayList<>(users.size());

        users.forEach(e -> userFriends.add(new UserFriend(e)));

        return userFriends;
    }


}
