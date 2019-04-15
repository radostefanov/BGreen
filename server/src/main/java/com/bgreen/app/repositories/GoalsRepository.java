package com.bgreen.app.repositories;

import com.bgreen.app.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Add CRUD functionality to Goal.
 */
public interface GoalsRepository extends JpaRepository<Goal, Long> {

}
