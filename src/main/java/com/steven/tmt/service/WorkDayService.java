package com.steven.tmt.service;

import com.steven.tmt.domain.IsHeadOf;
import com.steven.tmt.domain.IsSubsidiaryOf;
import com.steven.tmt.domain.User;
import com.steven.tmt.domain.WorkDay;
import com.steven.tmt.repository.WorkDayRepository;
import com.steven.tmt.security.SecurityUtils;
import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing WorkDay.
 */
@Service
@Transactional
public class WorkDayService {

    private final Logger log = LoggerFactory.getLogger(WorkDayService.class);

    private final WorkDayRepository workDayRepository;

    private final IsSubsidiaryOfService isSubsidiaryOfService;

    private final IsHeadOfService isHeadOfService;

    private final UserService userService;

    public WorkDayService(
        WorkDayRepository workDayRepository,
        IsSubsidiaryOfService isSubsidiaryOfService,
        IsHeadOfService isHeadOfService,
        UserService userService)
    {
        this.workDayRepository = workDayRepository;
        this.isSubsidiaryOfService = isSubsidiaryOfService;
        this.isHeadOfService = isHeadOfService;
        this.userService = userService;
    }

    /**
     * Save a workDay.
     *
     * @param workDay the entity to save
     * @return the persisted entity
     */
    public WorkDay save(WorkDay workDay) {
        log.debug("Request to save WorkDay : {}", workDay);
        WorkDay result = workDayRepository.save(workDay);
        return result;
    }

    /**
     *  Get all the workDays.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WorkDay> findAll() {
        log.debug("Request to get all WorkDays");
        List<WorkDay> result = workDayRepository.findAll();

        return result;
    }

    /**
     *  Get one workDay by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public WorkDay findOne(Long id) {
        log.debug("Request to get WorkDay : {}", id);
        WorkDay workDay = workDayRepository.findOne(id);
        return workDay;
    }

    /**
     *  Delete the  workDay by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkDay : {}", id);
        workDayRepository.delete(id);
    }

    /**
     *  Get all the workDays for current logged in user and users where he is subsidiary.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WorkDay> findAllForSubsidiary() {
        log.debug("Request to get all WorkDays for subsidiary");
        Optional<User> currentUser = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin());
        List<IsSubsidiaryOf> isSubsidiaryOfs = isSubsidiaryOfService.findBySubsidiaryIsCurrentUser();

        List<Long> userIds = new ArrayList<>();
        userIds.add(currentUser.get().getId());
        for (IsSubsidiaryOf isSubsidiaryOf : isSubsidiaryOfs) {
            userIds.add(isSubsidiaryOf.getEmployee().getId());
        }
        userIds = userIds.stream().distinct().collect(Collectors.toList());

        List<WorkDay> result = workDayRepository.findByUserIdIn(userIds);

        return result;
    }

    /**
     *  Get all the workDays for current logged in user and users where he is head.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WorkDay> findAllForHead() {
        log.debug("Request to get all WorkDays for head");
        List<IsHeadOf> isHeadOfs = isHeadOfService.findByHeadIsCurrentUser();

        List<Long> userIds = new ArrayList<>();
        for (IsHeadOf isHeadOf : isHeadOfs) {
            userIds.add(isHeadOf.getEmployee().getId());
        }
        userIds = userIds.stream().distinct().collect(Collectors.toList());

        List<WorkDay> result;
        if (userIds.size() == 0) {
            result = new ArrayList<>();
        } else {
            result = workDayRepository.findByUserIdInAndHoursApprovedOrExpensesApproved(userIds, false, false);
        }

        return result;
    }

    public WorkDay findOneByUserIdAndProjectIdAndDay(Long userId, Long projectId, LocalDate day) {
        return workDayRepository.findOneByUserIdAndProjectIdAndDay(userId, projectId, day);
    }
}
