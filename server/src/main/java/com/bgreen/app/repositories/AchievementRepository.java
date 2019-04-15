package com.bgreen.app.repositories;

import com.bgreen.app.models.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Add CRUD functionality for achievements.
 */

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    @Modifying
    @Query(
            value = "SET FOREIGN_KEY_CHECKS = 0; TRUNCATE table user_achievement;"
                    + " TRUNCATE table achievements; SET FOREIGN_KEY_CHECKS = 1; "
                    + "ALTER TABLE achievements ALTER COLUMN id RESTART WITH 1",
            nativeQuery = true
    )
    void truncate();
}
