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

    List<WorkDay> findByUserIdIn(List<Long> userIdList);

    @Query("select month(w.day), sum(case w.isHoliday when true then 1 else 0 end), sum(w.hours), sum(w.expenses) from WorkDay w where year(w.day) = ?1 group by month(w.day)")
    List<Object[]> findByYear(int year);

    @Query("select month(w.day), sum(case w.isHoliday when true then 1 else 0 end), sum(w.hours), sum(w.expenses) from WorkDay w where year(w.day) = ?1 and w.user.id = ?2 group by month(w.day)")
    List<Object[]> findByYearAndUserId(int year, Long userId);

    @Query("select month(w.day), sum(case w.isHoliday when true then 1 else 0 end), sum(w.hours), sum(w.expenses) from WorkDay w where year(w.day) = ?1 and w.project.id = ?2 group by month(w.day)")
    List<Object[]> findByYearAndProjectId(int year, Long projectId);

    @Query("select month(w.day), sum(case w.isHoliday when true then 1 else 0 end), sum(w.hours), sum(w.expenses) from WorkDay w where year(w.day) = ?1 and w.user.id = ?2 and w.project.id = ?3 group by month(w.day)")
    List<Object[]> findByYearAndUserIdAndProjectId(int year, Long userId, Long projectId);

    List<WorkDay> findByUserIdInAndHoursApprovedOrExpensesApproved(List<Long> userIds, boolean b, boolean b1);
}
