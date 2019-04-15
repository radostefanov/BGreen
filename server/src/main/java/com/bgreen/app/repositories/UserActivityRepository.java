package com.bgreen.app.repositories;

import com.bgreen.app.models.Activity;
import com.bgreen.app.models.User;
import com.bgreen.app.models.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {



    List<UserActivity> findAllByActivityAndUser(Activity activity, User user);

    List<UserActivity> findAllByUser(User user);

}
