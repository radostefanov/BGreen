package com.bgreen.app.services;

import com.bgreen.app.models.AddFriendRequest;
import com.bgreen.app.models.Friend;
import com.bgreen.app.models.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class FriendService extends AbstractService {


    /**
     * Creates a new Friend with given username.
     * @param username the username of the friend to be added
     * @return the created Friend
     */
    public Friend addFriend(String username) {

        AddFriendRequest friendRequest = new AddFriendRequest();
        friendRequest.setUsername(username);

        ResponseEntity<Friend> response = this.getRestTemplate().exchange(baseUrl + "api/friends",
                HttpMethod.POST, this.getHeaders(friendRequest), Friend.class);

        return response.getBody();
    }

    /**
     * Get all Friends of the user.
     * @return a list with all the friends of the user
     */
    public List<User> getFriends() {

        ResponseEntity<User[]> responseList = this.getRestTemplate().exchange(
                baseUrl + "api/friends/user", HttpMethod.GET, this.getHeaders(), User[].class);

        List<User> list = Arrays.asList(responseList.getBody());

        for (User user : list) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            user.setPoints(Double.parseDouble((df.format(user.getPoints())).replace(",", "")));
        }

        return list;
    }

    /**
     * Delete the friend with the given username.
     * @param username the username of the person to be deleted
     */
    public Friend deleteFriend(String username) {

        AddFriendRequest request = new AddFriendRequest();
        request.setUsername(username);

        ResponseEntity<Friend> response = this.getRestTemplate().exchange(baseUrl
                        + "api/friends/user", HttpMethod.POST,
                this.getHeaders(request), Friend.class);

        return response.getBody();

    }


    /**
     * accepts friend request.
     * @param username username of friendrequest
     * @return friend
     */
    public Friend acceptRequest(String username) {


        AddFriendRequest request = new AddFriendRequest();
        request.setUsername(username);

        ResponseEntity<Friend> response = this.getRestTemplate().exchange(baseUrl
                        + "api/friends/user/accept", HttpMethod.POST,
                this.getHeaders(request), Friend.class);

        return response.getBody();
    }

}
