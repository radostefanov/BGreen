package com.bgreen.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * User class to set properties for user.
 */
@Entity
@Table(name = "users")
public class User extends AbstractModel {

    /**
     * Username of the user.
     */
    @NotBlank
    @Size(min = 3, max = 100)
    private String username;

    /**
     * Password of the user.
     */
    @NotBlank
    @Size(min = 3, max = 100)
    private String password;

    /**
     * Email address of the user.
     */
    @Size(min = 3, max = 100)
    @Email
    private String email;

    /**
     * Total points of the user.
     */
    private double points;

    /**
     * Path to profile picture of user.
     */
    @Nullable
    @Size(min = 3, max = 100)
    private String profilePicture;

    /**
     * Daily user goal.
     */
    @Nullable
    private double dailyGoal;

    /**
     * Weekly user goal.
     */
    @Nullable
    private double weeklyGoal;

    /**
     * Monthly user goal.
     */
    @Nullable
    private double monthlyGoal;

    /**
     * Set of the activities a user is currently in.
     */
    @JsonIgnore
    @Nullable
    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private Set<UserActivity> activities = new HashSet<>();

    /**
     * Get the username of the user.
     * @return username of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set new username of the user.
     * @param username New username of user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password of the user.
     * @return password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set new password of the user.
     * @param password New password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get email of the user.
     * @return email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set new email address of the user.
     * @param email New email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get total points of the user.
     * @return total points.
     */
    public double getPoints() {
        return points;
    }

    /**
     * Set new total points of the user.
     * @param points new total points.
     */
    public void setPoints(double points) {
        this.points = points;
    }

    /**
     * Get path to profile picture of the user.
     * @return path to profile picture in /static.
     */
    public String getProfilePicture() {
        return profilePicture;
    }

    /**
     * Allow setting of profile picture path.
     * @param profilePicture is the new path to profile picture of the user.
     */
    public void setProfile_picture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * get daily point goal of user.
     * @return daily goal value.
     */
    public double getDailyGoal() {
        return dailyGoal;
    }

    /**
     * Sets updated value for daily goal.
     * @param dailyGoal updated daily goal value.
     */
    public void setDailyGoal(double dailyGoal) {
        this.dailyGoal = dailyGoal;
    }

    /**
     * get weekly point goal of user.
     * @return weekly goal value.
     */
    public double getWeeklyGoal() {
        return weeklyGoal;
    }

    /**
     * Sets updated value for weekly goal.
     * @param weeklyGoal updated weekly goal value.
     */
    public void setWeeklyGoal(double weeklyGoal) {
        this.weeklyGoal = weeklyGoal;
    }

    /**
     * get monthly point goal of user.
     * @return monthly goal value.
     */
    public double getMonthlyGoal() {
        return monthlyGoal;
    }

    /**
     * Sets updated value for monthly goal.
     * @param monthlyGoal updated monthly goal value.
     */
    public void setMonthlyGoal(double monthlyGoal) {
        this.monthlyGoal = monthlyGoal;
    }

    /**
     * Get the activites of the user.
     * @return A set of useractivities.
     */
    public Set<UserActivity> getActivities() {
        return activities;
    }

    public void setActivities(Set<UserActivity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return this.getUsername() + "/" + this.getPassword();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        User user = (User) object;
        return Objects.equals(username, user.username)
                && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = (int) (31 * result + points);
        return result;
    }
}
