package com.bgreen.app.reqmodels;

import com.bgreen.app.enums.FriendshipStatus;
import com.bgreen.app.models.User;

import java.util.Objects;


public class UserFriend {

    private String username;

    private double points;

    private String profilePicture;

    private FriendshipStatus friendshipStatus = FriendshipStatus.ACCEPTED;

    public UserFriend() {

    }

    /**
     * contructor for a userfriend.
     *
     * @param user the user
     */
    public UserFriend(User user) {

        this.username = user.getUsername();
        this.points = user.getPoints();
        this.profilePicture = user.getProfilePicture();
    }

    public FriendshipStatus getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(FriendshipStatus friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserFriend)) {
            return false;
        }
        UserFriend that = (UserFriend) obj;
        return Double.compare(that.points, points) == 0
                && Objects.equals(username, that.username)
                && Objects.equals(profilePicture, that.profilePicture)
                && friendshipStatus == that.friendshipStatus;
    }


}
