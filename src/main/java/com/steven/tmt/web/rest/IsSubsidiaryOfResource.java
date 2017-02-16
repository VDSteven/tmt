package com.steven.tmt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.steven.tmt.domain.IsSubsidiaryOf;
import com.steven.tmt.service.IsSubsidiaryOfService;
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
 * REST controller for managing IsSubsidiaryOf.
 */
@RestController
@RequestMapping("/api")
public class IsSubsidiaryOfResource {

    private final Logger log = LoggerFactory.getLogger(IsSubsidiaryOfResource.class);

    private static final String ENTITY_NAME = "isSubsidiaryOf";

    private final IsSubsidiaryOfService isSubsidiaryOfService;

    public IsSubsidiaryOfResource(IsSubsidiaryOfService isSubsidiaryOfService) {
        this.isSubsidiaryOfService = isSubsidiaryOfService;
    }

    /**
     * POST  /is-subsidiary-ofs : Create a new isSubsidiaryOf.
     *
     * @param isSubsidiaryOf the isSubsidiaryOf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new isSubsidiaryOf, or with status 400 (Bad Request) if the isSubsidiaryOf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/is-subsidiary-ofs")
    @Timed
    public ResponseEntity<IsSubsidiaryOf> createIsSubsidiaryOf(@Valid @RequestBody IsSubsidiaryOf isSubsidiaryOf) throws URISyntaxException {
        log.debug("REST request to save IsSubsidiaryOf : {}", isSubsidiaryOf);
        if (isSubsidiaryOf.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new isSubsidiaryOf cannot already have an ID")).body(null);
        }

        if (isSubsidiaryOfService.findOneByHeadAndEmployee(isSubsidiaryOf.getSubsidiary(), isSubsidiaryOf.getEmployee()) != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "uniqueconstraint", "This combination of subsidiary and employee already exists.")).body(null);
        }

        IsSubsidiaryOf result = isSubsidiaryOfService.save(isSubsidiaryOf);
        return ResponseEntity.created(new URI("/api/is-subsidiary-ofs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /is-subsidiary-ofs : Updates an existing isSubsidiaryOf.
     *
     * @param isSubsidiaryOf the isSubsidiaryOf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated isSubsidiaryOf,
     * or with status 400 (Bad Request) if the isSubsidiaryOf is not valid,
     * or with status 500 (Internal Server Error) if the isSubsidiaryOf couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/is-subsidiary-ofs")
    @Timed
    public ResponseEntity<IsSubsidiaryOf> updateIsSubsidiaryOf(@Valid @RequestBody IsSubsidiaryOf isSubsidiaryOf) throws URISyntaxException {
        log.debug("REST request to update IsSubsidiaryOf : {}", isSubsidiaryOf);
        if (isSubsidiaryOf.getId() == null) {
            return createIsSubsidiaryOf(isSubsidiaryOf);
        }
        IsSubsidiaryOf result = isSubsidiaryOfService.save(isSubsidiaryOf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, isSubsidiaryOf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /is-subsidiary-ofs : get all the isSubsidiaryOfs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of isSubsidiaryOfs in body
     */
    @GetMapping("/is-subsidiary-ofs")
    @Timed
    public List<IsSubsidiaryOf> getAllIsSubsidiaryOfs() {
        log.debug("REST request to get all IsSubsidiaryOfs");
        return isSubsidiaryOfService.findAll();
    }

    /**
     * GET  /is-subsidiary-ofs/:id : get the "id" isSubsidiaryOf.
     *
     * @param id the id of the isSubsidiaryOf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the isSubsidiaryOf, or with status 404 (Not Found)
     */
    @GetMapping("/is-subsidiary-ofs/{id}")
    @Timed
    public ResponseEntity<IsSubsidiaryOf> getIsSubsidiaryOf(@PathVariable Long id) {
        log.debug("REST request to get IsSubsidiaryOf : {}", id);
        IsSubsidiaryOf isSubsidiaryOf = isSubsidiaryOfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(isSubsidiaryOf));
    }

    /**
     * DELETE  /is-subsidiary-ofs/:id : delete the "id" isSubsidiaryOf.
     *
     * @param id the id of the isSubsidiaryOf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/is-subsidiary-ofs/{id}")
    @Timed
    public ResponseEntity<Void> deleteIsSubsidiaryOf(@PathVariable Long id) {
        log.debug("REST request to delete IsSubsidiaryOf : {}", id);
        isSubsidiaryOfService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
