package com.bgreen.app.models;

import com.bgreen.app.enums.FriendshipStatus;

/**
 * models user to be sent over http request in.
 * json format to be stored in remote database.
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String profilePicture;
    private double points;
    private double dailyGoal;
    private double weeklyGoal;
    private double monthlyGoal;

    private FriendshipStatus friendshipStatus;

    /**
     * Used in sendLogin to hide register information.
     *
     * @param username to send over http request
     * @param password post request parameters
     */
    public User(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Keep point-field on server side model null.
     *
     * @param username to be sent over post request
     * @param password to be sent over post request
     * @param email    additional field specific to register screen
     */
    public User(final String username, final String password, final String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * For ultimate send back to server side.
     *
     * @param username initialized
     * @param password initialized
     * @param email    initialized
     * @param points   initialized
     */
    public User(final String username, final String password, String email, int points) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.points = points;
    }

    /**
     * For use in statistics.
     *
     * @param username username received getUserInfo().
     * @param email    email received getUserInfo().
     * @param points   points received via getUserInfo().
     */
    public User(String username, String email, double points,
                double dailyGoal, double weeklyGoal, double monthlyGoal) {
        this.username = username;
        this.email = email;
        this.points = points;
        this.dailyGoal = dailyGoal;
        this.weeklyGoal = weeklyGoal;
        this.monthlyGoal = monthlyGoal;
    }

    /**
     * returns the uname of the user.
     *
     * @return username of user
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * returns the pw of the user.
     *
     * @return password String
     */
    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPoints() {
        return this.points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public double getDailyGoal() {
        return this.dailyGoal;
    }

    public double getWeeklyGoal() {
        return this.weeklyGoal;
    }

    public double getMonthlyGoal() {
        return this.monthlyGoal;
    }

    public void setDailyGoal(double dailyGoal) {
        this.dailyGoal = dailyGoal;
    }

    public void setWeeklyGoal(double weeklyGoal) {
        this.weeklyGoal = weeklyGoal;
    }

    public void setMonthlyGoal(double monthlyGoal) {
        this.monthlyGoal = monthlyGoal;
    }

    public FriendshipStatus getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(FriendshipStatus friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }
}