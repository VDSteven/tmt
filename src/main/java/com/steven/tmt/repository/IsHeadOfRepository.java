package com.steven.tmt.repository;

import com.steven.tmt.domain.IsHeadOf;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the IsHeadOf entity.
 */
@SuppressWarnings("unused")
public interface IsHeadOfRepository extends JpaRepository<IsHeadOf,Long> {

    @Query("select isHeadOf from IsHeadOf isHeadOf where isHeadOf.head.login = ?#{principal.username}")
    List<IsHeadOf> findByHeadIsCurrentUser();

    @Query("select isHeadOf from IsHeadOf isHeadOf where isHeadOf.employee.login = ?#{principal.username}")
    List<IsHeadOf> findByEmployeeIsCurrentUser();

}
