package com.steven.tmt.web.rest;

import com.steven.tmt.TmtApp;

import com.steven.tmt.domain.IsSubsidiaryOf;
import com.steven.tmt.domain.User;
import com.steven.tmt.domain.User;
import com.steven.tmt.repository.IsSubsidiaryOfRepository;
import com.steven.tmt.service.IsSubsidiaryOfService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the IsSubsidiaryOfResource REST controller.
 *
 * @see IsSubsidiaryOfResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmtApp.class)
public class IsSubsidiaryOfResourceIntTest {

    @Autowired
    private IsSubsidiaryOfRepository isSubsidiaryOfRepository;

    @Autowired
    private IsSubsidiaryOfService isSubsidiaryOfService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restIsSubsidiaryOfMockMvc;

    private IsSubsidiaryOf isSubsidiaryOf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IsSubsidiaryOfResource isSubsidiaryOfResource = new IsSubsidiaryOfResource(isSubsidiaryOfService);
        this.restIsSubsidiaryOfMockMvc = MockMvcBuilders.standaloneSetup(isSubsidiaryOfResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IsSubsidiaryOf createEntity(EntityManager em) {
        IsSubsidiaryOf isSubsidiaryOf = new IsSubsidiaryOf();
        // Add required entity
        User subsidiary = UserResourceIntTest.createEntity(em);
        em.persist(subsidiary);
        em.flush();
        isSubsidiaryOf.setSubsidiary(subsidiary);
        // Add required entity
        User employee = UserResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        isSubsidiaryOf.setEmployee(employee);
        return isSubsidiaryOf;
    }

    @Before
    public void initTest() {
        isSubsidiaryOf = createEntity(em);
    }

    @Test
    @Transactional
    public void createIsSubsidiaryOf() throws Exception {
        int databaseSizeBeforeCreate = isSubsidiaryOfRepository.findAll().size();

        // Create the IsSubsidiaryOf

        restIsSubsidiaryOfMockMvc.perform(post("/api/is-subsidiary-ofs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isSubsidiaryOf)))
            .andExpect(status().isCreated());

        // Validate the IsSubsidiaryOf in the database
        List<IsSubsidiaryOf> isSubsidiaryOfList = isSubsidiaryOfRepository.findAll();
        assertThat(isSubsidiaryOfList).hasSize(databaseSizeBeforeCreate + 1);
        IsSubsidiaryOf testIsSubsidiaryOf = isSubsidiaryOfList.get(isSubsidiaryOfList.size() - 1);
    }

    @Test
    @Transactional
    public void createIsSubsidiaryOfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = isSubsidiaryOfRepository.findAll().size();

        // Create the IsSubsidiaryOf with an existing ID
        IsSubsidiaryOf existingIsSubsidiaryOf = new IsSubsidiaryOf();
        existingIsSubsidiaryOf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIsSubsidiaryOfMockMvc.perform(post("/api/is-subsidiary-ofs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingIsSubsidiaryOf)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IsSubsidiaryOf> isSubsidiaryOfList = isSubsidiaryOfRepository.findAll();
        assertThat(isSubsidiaryOfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIsSubsidiaryOfs() throws Exception {
        // Initialize the database
        isSubsidiaryOfRepository.saveAndFlush(isSubsidiaryOf);

        // Get all the isSubsidiaryOfList
        restIsSubsidiaryOfMockMvc.perform(get("/api/is-subsidiary-ofs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isSubsidiaryOf.getId().intValue())));
    }

    @Test
    @Transactional
    public void getIsSubsidiaryOf() throws Exception {
        // Initialize the database
        isSubsidiaryOfRepository.saveAndFlush(isSubsidiaryOf);

        // Get the isSubsidiaryOf
        restIsSubsidiaryOfMockMvc.perform(get("/api/is-subsidiary-ofs/{id}", isSubsidiaryOf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(isSubsidiaryOf.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIsSubsidiaryOf() throws Exception {
        // Get the isSubsidiaryOf
        restIsSubsidiaryOfMockMvc.perform(get("/api/is-subsidiary-ofs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIsSubsidiaryOf() throws Exception {
        // Initialize the database
        isSubsidiaryOfService.save(isSubsidiaryOf);

        int databaseSizeBeforeUpdate = isSubsidiaryOfRepository.findAll().size();

        // Update the isSubsidiaryOf
        IsSubsidiaryOf updatedIsSubsidiaryOf = isSubsidiaryOfRepository.findOne(isSubsidiaryOf.getId());

        restIsSubsidiaryOfMockMvc.perform(put("/api/is-subsidiary-ofs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIsSubsidiaryOf)))
            .andExpect(status().isOk());

        // Validate the IsSubsidiaryOf in the database
        List<IsSubsidiaryOf> isSubsidiaryOfList = isSubsidiaryOfRepository.findAll();
        assertThat(isSubsidiaryOfList).hasSize(databaseSizeBeforeUpdate);
        IsSubsidiaryOf testIsSubsidiaryOf = isSubsidiaryOfList.get(isSubsidiaryOfList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingIsSubsidiaryOf() throws Exception {
        int databaseSizeBeforeUpdate = isSubsidiaryOfRepository.findAll().size();

        // Create the IsSubsidiaryOf

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIsSubsidiaryOfMockMvc.perform(put("/api/is-subsidiary-ofs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isSubsidiaryOf)))
            .andExpect(status().isCreated());

        // Validate the IsSubsidiaryOf in the database
        List<IsSubsidiaryOf> isSubsidiaryOfList = isSubsidiaryOfRepository.findAll();
        assertThat(isSubsidiaryOfList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIsSubsidiaryOf() throws Exception {
        // Initialize the database
        isSubsidiaryOfService.save(isSubsidiaryOf);

        int databaseSizeBeforeDelete = isSubsidiaryOfRepository.findAll().size();

        // Get the isSubsidiaryOf
        restIsSubsidiaryOfMockMvc.perform(delete("/api/is-subsidiary-ofs/{id}", isSubsidiaryOf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IsSubsidiaryOf> isSubsidiaryOfList = isSubsidiaryOfRepository.findAll();
        assertThat(isSubsidiaryOfList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IsSubsidiaryOf.class);
    }
}
