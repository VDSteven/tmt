package com.steven.tmt.web.rest;

import com.steven.tmt.TmtApp;

import com.steven.tmt.domain.WorkDay;
import com.steven.tmt.domain.User;
import com.steven.tmt.domain.Project;
import com.steven.tmt.repository.WorkDayRepository;
import com.steven.tmt.service.WorkDayService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WorkDayResource REST controller.
 *
 * @see WorkDayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TmtApp.class)
public class WorkDayResourceIntTest {

    private static final LocalDate DEFAULT_DAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAY = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_HOLIDAY = false;
    private static final Boolean UPDATED_IS_HOLIDAY = true;

    private static final Double DEFAULT_HOURS = 0D;
    private static final Double UPDATED_HOURS = 1D;

    private static final Boolean DEFAULT_HOURS_APPROVED = false;
    private static final Boolean UPDATED_HOURS_APPROVED = true;

    private static final Double DEFAULT_EXPENSES = 0D;
    private static final Double UPDATED_EXPENSES = 1D;

    private static final Boolean DEFAULT_EXPENSES_APPROVED = false;
    private static final Boolean UPDATED_EXPENSES_APPROVED = true;

    @Autowired
    private WorkDayRepository workDayRepository;

    @Autowired
    private WorkDayService workDayService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkDayMockMvc;

    private WorkDay workDay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkDayResource workDayResource = new WorkDayResource(workDayService);
        this.restWorkDayMockMvc = MockMvcBuilders.standaloneSetup(workDayResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkDay createEntity(EntityManager em) {
        WorkDay workDay = new WorkDay()
                .day(DEFAULT_DAY)
                .isHoliday(DEFAULT_IS_HOLIDAY)
                .hours(DEFAULT_HOURS)
                .hoursApproved(DEFAULT_HOURS_APPROVED)
                .expenses(DEFAULT_EXPENSES)
                .expensesApproved(DEFAULT_EXPENSES_APPROVED);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        workDay.setUser(user);
        // Add required entity
        Project project = ProjectResourceIntTest.createEntity(em);
        em.persist(project);
        em.flush();
        workDay.setProject(project);
        return workDay;
    }

    @Before
    public void initTest() {
        workDay = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkDay() throws Exception {
        int databaseSizeBeforeCreate = workDayRepository.findAll().size();

        // Create the WorkDay

        restWorkDayMockMvc.perform(post("/api/work-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDay)))
            .andExpect(status().isCreated());

        // Validate the WorkDay in the database
        List<WorkDay> workDayList = workDayRepository.findAll();
        assertThat(workDayList).hasSize(databaseSizeBeforeCreate + 1);
        WorkDay testWorkDay = workDayList.get(workDayList.size() - 1);
        assertThat(testWorkDay.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testWorkDay.isIsHoliday()).isEqualTo(DEFAULT_IS_HOLIDAY);
        assertThat(testWorkDay.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testWorkDay.isHoursApproved()).isEqualTo(DEFAULT_HOURS_APPROVED);
        assertThat(testWorkDay.getExpenses()).isEqualTo(DEFAULT_EXPENSES);
        assertThat(testWorkDay.isExpensesApproved()).isEqualTo(DEFAULT_EXPENSES_APPROVED);
    }

    @Test
    @Transactional
    public void createWorkDayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workDayRepository.findAll().size();

        // Create the WorkDay with an existing ID
        WorkDay existingWorkDay = new WorkDay();
        existingWorkDay.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkDayMockMvc.perform(post("/api/work-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingWorkDay)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WorkDay> workDayList = workDayRepository.findAll();
        assertThat(workDayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = workDayRepository.findAll().size();
        // set the field null
        workDay.setDay(null);

        // Create the WorkDay, which fails.

        restWorkDayMockMvc.perform(post("/api/work-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDay)))
            .andExpect(status().isBadRequest());

        List<WorkDay> workDayList = workDayRepository.findAll();
        assertThat(workDayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkDays() throws Exception {
        // Initialize the database
        workDayRepository.saveAndFlush(workDay);

        // Get all the workDayList
        restWorkDayMockMvc.perform(get("/api/work-days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workDay.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].isHoliday").value(hasItem(DEFAULT_IS_HOLIDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].hoursApproved").value(hasItem(DEFAULT_HOURS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].expenses").value(hasItem(DEFAULT_EXPENSES.doubleValue())))
            .andExpect(jsonPath("$.[*].expensesApproved").value(hasItem(DEFAULT_EXPENSES_APPROVED.booleanValue())));
    }

    @Test
    @Transactional
    public void getWorkDay() throws Exception {
        // Initialize the database
        workDayRepository.saveAndFlush(workDay);

        // Get the workDay
        restWorkDayMockMvc.perform(get("/api/work-days/{id}", workDay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workDay.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.isHoliday").value(DEFAULT_IS_HOLIDAY.booleanValue()))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS.doubleValue()))
            .andExpect(jsonPath("$.hoursApproved").value(DEFAULT_HOURS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.expenses").value(DEFAULT_EXPENSES.doubleValue()))
            .andExpect(jsonPath("$.expensesApproved").value(DEFAULT_EXPENSES_APPROVED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkDay() throws Exception {
        // Get the workDay
        restWorkDayMockMvc.perform(get("/api/work-days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkDay() throws Exception {
        // Initialize the database
        workDayService.save(workDay);

        int databaseSizeBeforeUpdate = workDayRepository.findAll().size();

        // Update the workDay
        WorkDay updatedWorkDay = workDayRepository.findOne(workDay.getId());
        updatedWorkDay
                .day(UPDATED_DAY)
                .isHoliday(UPDATED_IS_HOLIDAY)
                .hours(UPDATED_HOURS)
                .hoursApproved(UPDATED_HOURS_APPROVED)
                .expenses(UPDATED_EXPENSES)
                .expensesApproved(UPDATED_EXPENSES_APPROVED);

        restWorkDayMockMvc.perform(put("/api/work-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkDay)))
            .andExpect(status().isOk());

        // Validate the WorkDay in the database
        List<WorkDay> workDayList = workDayRepository.findAll();
        assertThat(workDayList).hasSize(databaseSizeBeforeUpdate);
        WorkDay testWorkDay = workDayList.get(workDayList.size() - 1);
        assertThat(testWorkDay.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testWorkDay.isIsHoliday()).isEqualTo(UPDATED_IS_HOLIDAY);
        assertThat(testWorkDay.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testWorkDay.isHoursApproved()).isEqualTo(UPDATED_HOURS_APPROVED);
        assertThat(testWorkDay.getExpenses()).isEqualTo(UPDATED_EXPENSES);
        assertThat(testWorkDay.isExpensesApproved()).isEqualTo(UPDATED_EXPENSES_APPROVED);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkDay() throws Exception {
        int databaseSizeBeforeUpdate = workDayRepository.findAll().size();

        // Create the WorkDay

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkDayMockMvc.perform(put("/api/work-days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workDay)))
            .andExpect(status().isCreated());

        // Validate the WorkDay in the database
        List<WorkDay> workDayList = workDayRepository.findAll();
        assertThat(workDayList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkDay() throws Exception {
        // Initialize the database
        workDayService.save(workDay);

        int databaseSizeBeforeDelete = workDayRepository.findAll().size();

        // Get the workDay
        restWorkDayMockMvc.perform(delete("/api/work-days/{id}", workDay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkDay> workDayList = workDayRepository.findAll();
        assertThat(workDayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkDay.class);
    }
}
