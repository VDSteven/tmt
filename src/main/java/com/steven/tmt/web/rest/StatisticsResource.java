package com.steven.tmt.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.steven.tmt.domain.WorkDay;
import com.steven.tmt.service.StatisticsService;
import com.steven.tmt.service.WorkDayService;
import com.steven.tmt.service.dto.StatisticsDTO;
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
 * REST controller for managing statistics.
 */
@RestController
@RequestMapping("/api")
public class StatisticsResource {
    private final Logger log = LoggerFactory.getLogger(StatisticsResource.class);

    private final StatisticsService statisticsService;

    public StatisticsResource(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * GET  /statistics : get work days statistics.
     *
     * @return List of StatisticsDTOs
     */
    @GetMapping("/statistics")
    @Timed
    public List<StatisticsDTO> getWorkDaysStatistics(@RequestParam("year") Integer year, @RequestParam("projectId") Optional<Long> projectId, @RequestParam("userId") Optional<Long> userId) {
        log.debug("REST request statistics");
        return statisticsService.getWorkDaysStatistics(year, projectId, userId);
    }
}
