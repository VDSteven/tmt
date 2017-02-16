package com.steven.tmt.repository;

import com.steven.tmt.domain.WorkDay;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkDay entity.
 */
@SuppressWarnings("unused")
public interface WorkDayRepository extends JpaRepository<WorkDay,Long> {

    @Query("select workDay from WorkDay workDay where workDay.user.login = ?#{principal.username}")
    List<WorkDay> findByUserIsCurrentUser();

}
