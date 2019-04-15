package com.bgreen.app.services;

import com.bgreen.app.models.Friend;
import com.bgreen.app.models.User;
import com.bgreen.app.repositories.FriendRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendsService {

    @Autowired
    private FriendRepository friendRepository;

    /**
     * Find friendship method.
     * Returns a friendship betweeen two users or null if it does not exist
     * @param user1 first user
     * @param user2 second user
     * @return Friendship
     */
    public Friend findFriendship(User user1, User user2) {

        Friend friend = friendRepository.findByUser1AndUser2(user1, user2);
        Friend friend2 = friendRepository.findByUser1AndUser2(user2, user1);

        if (friend != null) {
            return friend;
        } else {
            return friend2;
        }

    }
}
