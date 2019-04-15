package com.bgreen.app.repositories;

import com.bgreen.app.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Modifying
    @Query(
            value = "SET FOREIGN_KEY_CHECKS = 0; TRUNCATE table user_activity;"
                    + " TRUNCATE table activities; SET FOREIGN_KEY_CHECKS = 1; "
                    + "ALTER TABLE activities ALTER COLUMN id RESTART WITH 1",
            nativeQuery = true
    )
    void truncate();
}
