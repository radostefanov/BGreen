package com.bgreen.app.models;

import org.springframework.lang.Nullable;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friends")
public class Friend extends AbstractModel {


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user1_id",referencedColumnName = "id")
    @Nullable
    private User user1;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user2_id",referencedColumnName = "id")
    @Nullable
    private User user2;

    private boolean isAccepted;

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Friend friend = (Friend) object;
        return Objects.equals(user1, friend.user1)
                && Objects.equals(user2, friend.user2);
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}

