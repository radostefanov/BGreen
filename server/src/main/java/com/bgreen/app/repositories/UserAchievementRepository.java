package com.bgreen.app.repositories;

import com.bgreen.app.models.Achievement;
import com.bgreen.app.models.User;
import com.bgreen.app.models.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {

    List<UserAchievement> findAllByAchievementAndUser(Achievement achievement, User user);

    List<UserAchievement> findAllByUser(User user);
}
