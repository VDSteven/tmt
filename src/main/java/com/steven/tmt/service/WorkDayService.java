package com.steven.tmt.service;

import com.steven.tmt.domain.WorkDay;
import com.steven.tmt.repository.WorkDayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing WorkDay.
 */
@Service
@Transactional
public class WorkDayService {

    private final Logger log = LoggerFactory.getLogger(WorkDayService.class);
    
    private final WorkDayRepository workDayRepository;

    public WorkDayService(WorkDayRepository workDayRepository) {
        this.workDayRepository = workDayRepository;
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
}
