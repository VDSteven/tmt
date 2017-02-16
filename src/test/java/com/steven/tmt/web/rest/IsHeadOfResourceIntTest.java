package com.steven.tmt.web.rest;

import com.steven.tmt.TmtApp;

import com.steven.tmt.domain.IsHeadOf;
import com.steven.tmt.domain.User;
import com.steven.tmt.domain.User;
import com.steven.tmt.repository.IsHeadOfRepository;
import com.steven.tmt.service.IsHeadOfService;

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
 * Test class for the IsHeadOfResource REST controller.
 *
 * @see IsHeadOfResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmtApp.class)
public class IsHeadOfResourceIntTest {

    @Autowired
    private IsHeadOfRepository isHeadOfRepository;

    @Autowired
    private IsHeadOfService isHeadOfService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restIsHeadOfMockMvc;

    private IsHeadOf isHeadOf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IsHeadOfResource isHeadOfResource = new IsHeadOfResource(isHeadOfService);
        this.restIsHeadOfMockMvc = MockMvcBuilders.standaloneSetup(isHeadOfResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IsHeadOf createEntity(EntityManager em) {
        IsHeadOf isHeadOf = new IsHeadOf();
        // Add required entity
        User head = UserResourceIntTest.createEntity(em);
        em.persist(head);
        em.flush();
        isHeadOf.setHead(head);
        // Add required entity
        User employee = UserResourceIntTest.createEntity(em);
        em.persist(employee);
        em.flush();
        isHeadOf.setEmployee(employee);
        return isHeadOf;
    }

    @Before
    public void initTest() {
        isHeadOf = createEntity(em);
    }

    @Test
    @Transactional
    public void createIsHeadOf() throws Exception {
        int databaseSizeBeforeCreate = isHeadOfRepository.findAll().size();

        // Create the IsHeadOf

        restIsHeadOfMockMvc.perform(post("/api/is-head-ofs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isHeadOf)))
            .andExpect(status().isCreated());

        // Validate the IsHeadOf in the database
        List<IsHeadOf> isHeadOfList = isHeadOfRepository.findAll();
        assertThat(isHeadOfList).hasSize(databaseSizeBeforeCreate + 1);
        IsHeadOf testIsHeadOf = isHeadOfList.get(isHeadOfList.size() - 1);
    }

    @Test
    @Transactional
    public void createIsHeadOfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = isHeadOfRepository.findAll().size();

        // Create the IsHeadOf with an existing ID
        IsHeadOf existingIsHeadOf = new IsHeadOf();
        existingIsHeadOf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIsHeadOfMockMvc.perform(post("/api/is-head-ofs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingIsHeadOf)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IsHeadOf> isHeadOfList = isHeadOfRepository.findAll();
        assertThat(isHeadOfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIsHeadOfs() throws Exception {
        // Initialize the database
        isHeadOfRepository.saveAndFlush(isHeadOf);

        // Get all the isHeadOfList
        restIsHeadOfMockMvc.perform(get("/api/is-head-ofs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(isHeadOf.getId().intValue())));
    }

    @Test
    @Transactional
    public void getIsHeadOf() throws Exception {
        // Initialize the database
        isHeadOfRepository.saveAndFlush(isHeadOf);

        // Get the isHeadOf
        restIsHeadOfMockMvc.perform(get("/api/is-head-ofs/{id}", isHeadOf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(isHeadOf.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIsHeadOf() throws Exception {
        // Get the isHeadOf
        restIsHeadOfMockMvc.perform(get("/api/is-head-ofs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIsHeadOf() throws Exception {
        // Initialize the database
        isHeadOfService.save(isHeadOf);

        int databaseSizeBeforeUpdate = isHeadOfRepository.findAll().size();

        // Update the isHeadOf
        IsHeadOf updatedIsHeadOf = isHeadOfRepository.findOne(isHeadOf.getId());

        restIsHeadOfMockMvc.perform(put("/api/is-head-ofs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIsHeadOf)))
            .andExpect(status().isOk());

        // Validate the IsHeadOf in the database
        List<IsHeadOf> isHeadOfList = isHeadOfRepository.findAll();
        assertThat(isHeadOfList).hasSize(databaseSizeBeforeUpdate);
        IsHeadOf testIsHeadOf = isHeadOfList.get(isHeadOfList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingIsHeadOf() throws Exception {
        int databaseSizeBeforeUpdate = isHeadOfRepository.findAll().size();

        // Create the IsHeadOf

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIsHeadOfMockMvc.perform(put("/api/is-head-ofs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(isHeadOf)))
            .andExpect(status().isCreated());

        // Validate the IsHeadOf in the database
        List<IsHeadOf> isHeadOfList = isHeadOfRepository.findAll();
        assertThat(isHeadOfList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIsHeadOf() throws Exception {
        // Initialize the database
        isHeadOfService.save(isHeadOf);

        int databaseSizeBeforeDelete = isHeadOfRepository.findAll().size();

        // Get the isHeadOf
        restIsHeadOfMockMvc.perform(delete("/api/is-head-ofs/{id}", isHeadOf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IsHeadOf> isHeadOfList = isHeadOfRepository.findAll();
        assertThat(isHeadOfList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IsHeadOf.class);
    }
}
