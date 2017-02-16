package com.steven.tmt.service;

import com.steven.tmt.domain.IsSubsidiaryOf;
import com.steven.tmt.domain.User;
import com.steven.tmt.domain.WorkDay;
import com.steven.tmt.repository.WorkDayRepository;
import com.steven.tmt.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing WorkDay.
 */
@Service
@Transactional
public class WorkDayService {

    private final Logger log = LoggerFactory.getLogger(WorkDayService.class);

    private final WorkDayRepository workDayRepository;

    private final IsSubsidiaryOfService isSubsidiaryOfService;

    private final UserService userService;

    public WorkDayService(
        WorkDayRepository workDayRepository,
        IsSubsidiaryOfService isSubsidiaryOfService,
        UserService userService)
    {
        this.workDayRepository = workDayRepository;
        this.isSubsidiaryOfService = isSubsidiaryOfService;
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
    public List<WorkDay> findAllBySubsidiary() {
        log.debug("Request to get all WorkDays by subsidiary");
        Optional<User> currentUser = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin());
        List<IsSubsidiaryOf> isSubsidiaryOfs = isSubsidiaryOfService.findBySubsidiaryIsCurrentUser();

        List<Long> userIds = new ArrayList<>();
        userIds.add(currentUser.get().getId());
        for (IsSubsidiaryOf isSubsidiaryOf : isSubsidiaryOfs) {
            userIds.add(isSubsidiaryOf.getEmployee().getId());
        }

        List<WorkDay> result = workDayRepository.findByUserIdIn(userIds);

        return result;
    }
}
