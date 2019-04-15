package com.bgreen.app.controllers;

import com.bgreen.app.enums.FriendshipStatus;
import com.bgreen.app.models.Friend;
import com.bgreen.app.models.User;
import com.bgreen.app.repositories.FriendRepository;
import com.bgreen.app.repositories.UserRepository;
import com.bgreen.app.reqmodels.AddFriendRequest;
import com.bgreen.app.reqmodels.UserFriend;
import com.bgreen.app.services.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;



@RestController
public class FriendController {

    /**
     * Inject the Friend service for the main logic of friends.
     */
    @Autowired
    public FriendsService friendsService;

    /**
     * Inject the Friend repository for controlling all data for activities.
     */
    @Autowired
    public FriendRepository friendRepository;

    /**
     * Inject the User repository for controlling all data for activities.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * List all friends.
     *
     * @return Returns array with all friends in the database
     */
    @GetMapping("/api/friends")
    public List<Friend> getAllFriends() {

        return friendRepository.findAll();
    }

    /**
     * Create a new friend in the database.
     *
     * @param friend Friend to be added to database
     * @return The created Friend
     */
    @PostMapping("/api/friends")
    public ResponseEntity<Friend> createNewFriend(@RequestBody AddFriendRequest friend,
                                                  Authentication authentication) {

        User loggedInUser = (User) authentication.getCredentials();

        if (loggedInUser.getUsername().equals(friend.getUsername())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User correctUser = userRepository.findUserByUsername(loggedInUser.getUsername());

        User friendUser = userRepository.findUserByUsername(friend.getUsername());

        if (friendUser != null) {


            if (checkIfFriend(correctUser, friendUser) == false) {

                Friend friendship = new Friend();

                friendship.setUser1(correctUser);
                friendship.setUser2(friendUser);

                return new ResponseEntity<Friend>(friendRepository.save(friendship),
                        HttpStatus.OK);

            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get a specific friend by given ID.
     *
     * @param id ID of the friend to in the database
     * @return The friend with the given ID
     */
    @GetMapping("/api/friends/{id}")
    public ResponseEntity<Friend> getFriendById(@PathVariable(value = "id") long id) {

        return friendRepository.findById(id).map(e -> ResponseEntity.ok(e))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes friend with given ID from database.
     *
     * @param id ID of the friend to be deleted
     * @return status 200 if the friend was deleted or 404 if the friend was not found
     */
    @DeleteMapping("/api/friends/{id}")
    public ResponseEntity deleteFriend(@PathVariable(value = "id") long id) {

        return friendRepository.findById(id).map(friend -> {

            friendRepository.delete(friend);

            return new ResponseEntity(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes the friend with given username from database.
     * @param request the request.
     * @param authentication the authentication of the user.
     * @return status 200 if deleted, else 404.
     */
    @PostMapping("/api/friends/user/accept")
    public ResponseEntity acceptFriendship(@Valid @RequestBody AddFriendRequest request,
                                                 Authentication authentication) {

        User loggedInUser = (User) authentication.getCredentials();

        User correctUser = userRepository.findUserByUsername(loggedInUser.getUsername());

        User friendUser = userRepository.findUserByUsername(request.getUsername());

        if (friendUser != null) {

            Friend friend = friendsService.findFriendship(correctUser, friendUser);

            if (friend != null) {
                friend.setAccepted(true);
                friendRepository.save(friend);
                return new ResponseEntity(HttpStatus.OK);
            }

        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes the friend with given username from database.
     * @param request the request.
     * @param authentication the authentication of the user.
     * @return status 200 if deleted, else 404.
     */
    @PostMapping("/api/friends/user")
    public ResponseEntity deleteFriendByUsername(@Valid @RequestBody AddFriendRequest request,
                                                 Authentication authentication) {

        User loggedInUser = (User) authentication.getCredentials();

        User correctUser = userRepository.findUserByUsername(loggedInUser.getUsername());

        User friendUser = userRepository.findUserByUsername(request.getUsername());

        if (friendUser != null) {
            Friend friendship = friendsService.findFriendship(correctUser, friendUser);

            if (friendship != null) {
                friendRepository.delete(friendship);
                return new ResponseEntity(HttpStatus.OK);
            }

        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    /**
     * Lists all users that a the given user is friends with.
     * @param authentication authentication of the user whose friend you want
     * @return List with all users that the user with the given ID is friends with
     */
    @GetMapping("/api/friends/user")
    public List<UserFriend> getAllFriendsFromUser(Authentication authentication) {

        User loggedInUser = (User) authentication.getCredentials();

        List<Friend> friendResult = friendRepository.findAllByUser1(loggedInUser);
        List<Friend> friendResult2 = friendRepository.findAllByUser2(loggedInUser);

        List<UserFriend> result = new ArrayList<UserFriend>();
        for (Friend friend : friendResult) {
            UserFriend usrFriend = new UserFriend(friend.getUser2());
            if (!friend.isAccepted()) {
                usrFriend.setPoints(0);
                usrFriend.setProfilePicture(null);
                usrFriend.setFriendshipStatus(FriendshipStatus.WAITING_FOR_ANSWER);
            }
            result.add(usrFriend);
        }
        for (Friend friend : friendResult2) {
            UserFriend usrFriend =  new UserFriend(friend.getUser1());
            if (!friend.isAccepted()) {
                usrFriend.setPoints(0);
                usrFriend.setProfilePicture(null);
                usrFriend.setFriendshipStatus(FriendshipStatus.HAS_TO_RESPOND);
            }
            result.add(usrFriend);
        }

        result.sort((user1, user2) -> user1.getPoints() - user2.getPoints() > 0 ? -1 : 1);

        return result;

    }

    /**
     * checks if two users are friends.
     * @param user1 First user you compare.
     * @param user2 Second user you compare.
     * @return True or false depending on the friendship of the 2 users.
     */
    public boolean checkIfFriend(User user1, User user2) {

        List<Friend> friend1 = friendRepository.findAllByUser1(user1);
        List<Friend> friend2 = friendRepository.findAllByUser2(user1);

        for (Friend friend: friend1) {
            if (friend.getUser2().equals(user2)) {
                return true;
            }
        }

        for (Friend friend: friend2) {
            if (friend.getUser1().equals(user2)) {
                return true;
            }
        }

        return false;

    }
}
