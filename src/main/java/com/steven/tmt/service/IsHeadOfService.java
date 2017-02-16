package com.steven.tmt.service;

import com.steven.tmt.domain.IsHeadOf;
import com.steven.tmt.domain.User;
import com.steven.tmt.repository.IsHeadOfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing IsHeadOf.
 */
@Service
@Transactional
public class IsHeadOfService {

    private final Logger log = LoggerFactory.getLogger(IsHeadOfService.class);

    private final IsHeadOfRepository isHeadOfRepository;

    public IsHeadOfService(IsHeadOfRepository isHeadOfRepository) {
        this.isHeadOfRepository = isHeadOfRepository;
    }

    /**
     * Save a isHeadOf.
     *
     * @param isHeadOf the entity to save
     * @return the persisted entity
     */
    public IsHeadOf save(IsHeadOf isHeadOf) {
        log.debug("Request to save IsHeadOf : {}", isHeadOf);
        IsHeadOf result = isHeadOfRepository.save(isHeadOf);
        return result;
    }

    /**
     *  Get all the isHeadOfs.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<IsHeadOf> findAll() {
        log.debug("Request to get all IsHeadOfs");
        List<IsHeadOf> result = isHeadOfRepository.findAll();

        return result;
    }

    /**
     *  Get one isHeadOf by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public IsHeadOf findOne(Long id) {
        log.debug("Request to get IsHeadOf : {}", id);
        IsHeadOf isHeadOf = isHeadOfRepository.findOne(id);
        return isHeadOf;
    }

    /**
     *  Delete the  isHeadOf by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IsHeadOf : {}", id);
        isHeadOfRepository.delete(id);
    }

    /**
     *  Get all the isHeadOfs where the current user is head.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<IsHeadOf> findByHeadIsCurrentUser() {
        log.debug("Request to get all IsHeadOfs");
        List<IsHeadOf> result = isHeadOfRepository.findByHeadIsCurrentUser();

        return result;
    }

    public IsHeadOf findOneByHeadAndEmployee(User head, User employee) {
        return isHeadOfRepository.findOneByHeadAndEmployee(head, employee);
    }
}
