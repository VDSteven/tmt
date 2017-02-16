package com.steven.tmt.service;

import com.steven.tmt.domain.IsSubsidiaryOf;
import com.steven.tmt.repository.IsSubsidiaryOfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing IsSubsidiaryOf.
 */
@Service
@Transactional
public class IsSubsidiaryOfService {

    private final Logger log = LoggerFactory.getLogger(IsSubsidiaryOfService.class);
    
    private final IsSubsidiaryOfRepository isSubsidiaryOfRepository;

    public IsSubsidiaryOfService(IsSubsidiaryOfRepository isSubsidiaryOfRepository) {
        this.isSubsidiaryOfRepository = isSubsidiaryOfRepository;
    }

    /**
     * Save a isSubsidiaryOf.
     *
     * @param isSubsidiaryOf the entity to save
     * @return the persisted entity
     */
    public IsSubsidiaryOf save(IsSubsidiaryOf isSubsidiaryOf) {
        log.debug("Request to save IsSubsidiaryOf : {}", isSubsidiaryOf);
        IsSubsidiaryOf result = isSubsidiaryOfRepository.save(isSubsidiaryOf);
        return result;
    }

    /**
     *  Get all the isSubsidiaryOfs.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<IsSubsidiaryOf> findAll() {
        log.debug("Request to get all IsSubsidiaryOfs");
        List<IsSubsidiaryOf> result = isSubsidiaryOfRepository.findAll();

        return result;
    }

    /**
     *  Get one isSubsidiaryOf by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public IsSubsidiaryOf findOne(Long id) {
        log.debug("Request to get IsSubsidiaryOf : {}", id);
        IsSubsidiaryOf isSubsidiaryOf = isSubsidiaryOfRepository.findOne(id);
        return isSubsidiaryOf;
    }

    /**
     *  Delete the  isSubsidiaryOf by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IsSubsidiaryOf : {}", id);
        isSubsidiaryOfRepository.delete(id);
    }
}
