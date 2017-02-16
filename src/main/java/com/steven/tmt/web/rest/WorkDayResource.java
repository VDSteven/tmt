package com.steven.tmt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.steven.tmt.domain.WorkDay;
import com.steven.tmt.service.WorkDayService;
import com.steven.tmt.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WorkDay.
 */
@RestController
@RequestMapping("/api")
public class WorkDayResource {

    private final Logger log = LoggerFactory.getLogger(WorkDayResource.class);

    private static final String ENTITY_NAME = "workDay";

    private final WorkDayService workDayService;

    public WorkDayResource(WorkDayService workDayService) {
        this.workDayService = workDayService;
    }

    /**
     * POST  /work-days : Create a new workDay.
     *
     * @param workDay the workDay to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workDay, or with status 400 (Bad Request) if the workDay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-days")
    @Timed
    public ResponseEntity<WorkDay> createWorkDay(@Valid @RequestBody WorkDay workDay) throws URISyntaxException {
        log.debug("REST request to save WorkDay : {}", workDay);
        if (workDay.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new workDay cannot already have an ID")).body(null);
        }
        WorkDay result = workDayService.save(workDay);
        return ResponseEntity.created(new URI("/api/work-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-days : Updates an existing workDay.
     *
     * @param workDay the workDay to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workDay,
     * or with status 400 (Bad Request) if the workDay is not valid,
     * or with status 500 (Internal Server Error) if the workDay couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-days")
    @Timed
    public ResponseEntity<WorkDay> updateWorkDay(@Valid @RequestBody WorkDay workDay) throws URISyntaxException {
        log.debug("REST request to update WorkDay : {}", workDay);
        if (workDay.getId() == null) {
            return createWorkDay(workDay);
        }
        WorkDay result = workDayService.save(workDay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workDay.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-days : get all the workDays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workDays in body
     */
    @GetMapping("/work-days")
    @Timed
    public List<WorkDay> getAllWorkDays() {
        log.debug("REST request to get all WorkDays");
        return workDayService.findAll();
    }

    /**
     * GET  /work-days/:id : get the "id" workDay.
     *
     * @param id the id of the workDay to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workDay, or with status 404 (Not Found)
     */
    @GetMapping("/work-days/{id}")
    @Timed
    public ResponseEntity<WorkDay> getWorkDay(@PathVariable Long id) {
        log.debug("REST request to get WorkDay : {}", id);
        WorkDay workDay = workDayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workDay));
    }

    /**
     * DELETE  /work-days/:id : delete the "id" workDay.
     *
     * @param id the id of the workDay to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-days/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkDay(@PathVariable Long id) {
        log.debug("REST request to delete WorkDay : {}", id);
        workDayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /work-days : get all the workDays for current user and user where he is subsidiary.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workDays in body
     */
    @GetMapping("/work-days/subsidiary")
    @Timed
    public List<WorkDay> getAllWorkDaysForSubsidiary() {
        log.debug("REST request to get all WorkDays by subsidiary");
        return workDayService.findAllBySubsidiary();
    }
}
