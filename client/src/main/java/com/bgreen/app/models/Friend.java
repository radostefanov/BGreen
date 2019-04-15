package com.bgreen.app.models;

public class Friend {

    private User user1;
    private User user2;

    private boolean isAccepted = false;

    /**
     * Contrcuts a friend object.
     * @param user1 The first user
     * @param user2 The second user
     */
    public Friend(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    /**
     * Get user1.
     * @return The first user
     */
    public User getUser1() {
        return user1;
    }

    /**
     * Set a new User for user1.
     * @param user1 The new User
     */
    public void setUser1(User user1) {
        this.user1 = user1;
    }

    /**
     * Get user2.
     * @return The second user
     */
    public User getUser2() {
        return user2;
    }

    /**
     * Set a new User fo user2.
     * @param user2 The new user.
     */
    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
