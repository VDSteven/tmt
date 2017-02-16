package com.steven.tmt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.steven.tmt.domain.IsHeadOf;
import com.steven.tmt.service.IsHeadOfService;
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
 * REST controller for managing IsHeadOf.
 */
@RestController
@RequestMapping("/api")
public class IsHeadOfResource {

    private final Logger log = LoggerFactory.getLogger(IsHeadOfResource.class);

    private static final String ENTITY_NAME = "isHeadOf";

    private final IsHeadOfService isHeadOfService;

    public IsHeadOfResource(IsHeadOfService isHeadOfService) {
        this.isHeadOfService = isHeadOfService;
    }

    /**
     * POST  /is-head-ofs : Create a new isHeadOf.
     *
     * @param isHeadOf the isHeadOf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new isHeadOf, or with status 400 (Bad Request) if the isHeadOf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-head-ofs")
    @Timed
    public ResponseEntity<IsHeadOf> createIsHeadOf(@Valid @RequestBody IsHeadOf isHeadOf) throws URISyntaxException {
        log.debug("REST request to save IsHeadOf : {}", isHeadOf);
        if (isHeadOf.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new isHeadOf cannot already have an ID")).body(null);
        }

        if (isHeadOfService.findOneByHeadAndEmployee(isHeadOf.getHead(), isHeadOf.getEmployee()) != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "uniqueconstraint", "This combination of head and employee already exists.")).body(null);
        }

        IsHeadOf result = isHeadOfService.save(isHeadOf);
        return ResponseEntity.created(new URI("/api/is-head-ofs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-head-ofs : Updates an existing isHeadOf.
     *
     * @param isHeadOf the isHeadOf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated isHeadOf,
     * or with status 400 (Bad Request) if the isHeadOf is not valid,
     * or with status 500 (Internal Server Error) if the isHeadOf couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-head-ofs")
    @Timed
    public ResponseEntity<IsHeadOf> updateIsHeadOf(@Valid @RequestBody IsHeadOf isHeadOf) throws URISyntaxException {
        log.debug("REST request to update IsHeadOf : {}", isHeadOf);
        if (isHeadOf.getId() == null) {
            return createIsHeadOf(isHeadOf);
        }
        IsHeadOf result = isHeadOfService.save(isHeadOf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, isHeadOf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-head-ofs : get all the isHeadOfs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of isHeadOfs in body
     */
    @GetMapping("/is-head-ofs")
    @Timed
    public List<IsHeadOf> getAllIsHeadOfs() {
        log.debug("REST request to get all IsHeadOfs");
        return isHeadOfService.findAll();
    }

    /**
     * GET  /is-head-ofs/:id : get the "id" isHeadOf.
     *
     * @param id the id of the isHeadOf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the isHeadOf, or with status 404 (Not Found)
     */
    @GetMapping("/is-head-ofs/{id}")
    @Timed
    public ResponseEntity<IsHeadOf> getIsHeadOf(@PathVariable Long id) {
        log.debug("REST request to get IsHeadOf : {}", id);
        IsHeadOf isHeadOf = isHeadOfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(isHeadOf));
    }

    /**
     * DELETE  /is-head-ofs/:id : delete the "id" isHeadOf.
     *
     * @param id the id of the isHeadOf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-head-ofs/{id}")
    @Timed
    public ResponseEntity<Void> deleteIsHeadOf(@PathVariable Long id) {
        log.debug("REST request to delete IsHeadOf : {}", id);
        isHeadOfService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
