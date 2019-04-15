package com.bgreen.app.repositories;

import com.bgreen.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


/**
 * Created by radoslavstefanov on 25.02.19.
 */
@RepositoryRestResource()
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user in the database with a certain username.
     * @param username Username of the user to return.
     * @return List with Users with provided username.
     */
    User findUserByUsername(String username);

    /**
     * Find user in the database with a certain email address.
     * @param email email address of the user to return.
     * @return List with Users with provided username.
     */
    User findUserByEmail(String email);

    @Modifying
    @Query(
            value = "SET FOREIGN_KEY_CHECKS = 0; "
                    + "TRUNCATE table user_activity; TRUNCATE table users;"
                    + " SET FOREIGN_KEY_CHECKS = 1; "
                    + "ALTER TABLE users ALTER COLUMN id RESTART WITH 1",
            nativeQuery = true
    )
    void truncate();


    List<User> findTop5ByOrderByPointsDesc();
}
