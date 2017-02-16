package com.steven.tmt.service;

import com.steven.tmt.repository.WorkDayRepository;
import com.steven.tmt.service.dto.StatisticsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for statistics.
 */
@Service
@Transactional
public class StatisticsService {

    private final Logger log = LoggerFactory.getLogger(WorkDayService.class);

    private final WorkDayRepository workDayRepository;

    public StatisticsService (WorkDayRepository workDayRepository) {
        this.workDayRepository = workDayRepository;
    }

    @Transactional(readOnly = true)
    public List<StatisticsDTO> getWorkDaysStatistics(Integer year, Optional<Long> projectId, Optional<Long> userId) {
        List<Object[]> stats;
        if (projectId.isPresent() && userId.isPresent()) {
            stats = workDayRepository.findByYearAndUserIdAndProjectId(year, userId.get(), projectId.get());
        } else if (projectId.isPresent() && !userId.isPresent()) {
            stats = workDayRepository.findByYearAndProjectId(year, projectId.get());
        } else if (!projectId.isPresent() && userId.isPresent()) {
            stats = workDayRepository.findByYearAndUserId(year, userId.get());
        } else {
            stats = workDayRepository.findByYear(year);
        }

        return mapQueryResultToStatisticsDTO(stats);
    }

    public List<StatisticsDTO> mapQueryResultToStatisticsDTO(List<Object[]> queryResults) {
        List<StatisticsDTO> stats = new ArrayList<>();
        Iterator<Object[]> resultsIterator = queryResults.iterator();
        Object[] resultObject = null;
        if (resultsIterator.hasNext()) {
            resultObject = resultsIterator.next();
        }

        for (int i=1; i<13; i++) {
            StatisticsDTO stat;
            if (resultObject != null && (Integer)resultObject[0] == i) {
                stat = new StatisticsDTO(i,(Long)resultObject[1],(Double) resultObject[2],(Double) resultObject[3]);
                if (stat.getHours() == null) {
                    stat.setHours(Double.valueOf(0));
                }
                if (stat.getExpenses() == null) {
                    stat.setExpenses(Double.valueOf(0));
                }
                resultObject = resultsIterator.hasNext() ? resultsIterator.next() : null;
            } else {
                stat = new StatisticsDTO(i,Long.valueOf(0),Double.valueOf(0),Double.valueOf(0));
            }
            stats.add(stat);
        }

        return stats;
    }
}
