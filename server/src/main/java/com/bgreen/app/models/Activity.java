package com.bgreen.app.models;

import com.bgreen.app.enums.ActivityCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "activities")
public class Activity extends AbstractModel {

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    private int co2Points;

    private int co2Interval;

    @NotNull
    private ActivityCategory category;

    @JsonIgnore
    @Nullable
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    private Set<UserActivity> users = new HashSet<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCo2Points() {
        return co2Points;
    }

    public void setCo2Points(int co2Points) {
        this.co2Points = co2Points;
    }

    public int getCo2Interval() {
        return co2Interval;
    }

    public void setCo2Interval(int co2Interval) {
        this.co2Interval = co2Interval;
    }

    public Set<UserActivity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserActivity> users) {
        this.users = users;
    }

    public ActivityCategory getCategory() {
        return category;
    }

    public void setCategory(ActivityCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Activity activity = (Activity) object;

        if (co2Points != activity.co2Points) {
            return false;
        }
        if (co2Interval != activity.co2Interval) {
            return false;
        }
        return name.equals(activity.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + co2Points;
        result = 31 * result + co2Interval;
        return result;
    }

    @Override
    public String toString() {
        return "Activity{"
                + "name='"
                + name
                + '\''
                + ", co2Points="
                + co2Points
                + ", co2Interval="
                + co2Interval
                + ", category="
                + category
                + ", users="
                + users
                + '}';
    }
}
