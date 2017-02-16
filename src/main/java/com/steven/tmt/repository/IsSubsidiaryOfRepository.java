package com.steven.tmt.repository;

import com.steven.tmt.domain.IsSubsidiaryOf;

import com.steven.tmt.domain.User;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the IsSubsidiaryOf entity.
 */
@SuppressWarnings("unused")
public interface IsSubsidiaryOfRepository extends JpaRepository<IsSubsidiaryOf,Long> {

    @Query("select isSubsidiaryOf from IsSubsidiaryOf isSubsidiaryOf where isSubsidiaryOf.subsidiary.login = ?#{principal.username}")
    List<IsSubsidiaryOf> findBySubsidiaryIsCurrentUser();

    @Query("select isSubsidiaryOf from IsSubsidiaryOf isSubsidiaryOf where isSubsidiaryOf.employee.login = ?#{principal.username}")
    List<IsSubsidiaryOf> findByEmployeeIsCurrentUser();

    IsSubsidiaryOf findOneBySubsidiaryAndEmployee(User subsidiary, User employee);
}
