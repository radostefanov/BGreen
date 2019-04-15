package com.bgreen.app.repositories;

import com.bgreen.app.models.Friend;
import com.bgreen.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Add CRUD functionality for friends.
 */
public interface FriendRepository extends JpaRepository
        <Friend, Long> {

    List<Friend> findAllByUser1_id(long user1Id);

    List<Friend> findAllByUser2_id(long user2Id);

    List<Friend> findAllByUser1(User user1);

    List<Friend> findAllByUser2(User user2);

    Friend findByUser1AndUser2(User user1, User user2);


}