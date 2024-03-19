package com.fourkites.facilities.dy.eventdataingestion.dao;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SwitcherEventLogDAO {
    private final JdbcTemplate jdbcTemplate;

    public Integer add(Integer switcherEventMasterId, String tenant, Map<String, Object> eventData, Timestamp eventTime) {
        String insertQuery = "INSERT INTO switcher_event_log (switcher_event_master_id, tenant, event_data, event_time, created_at) VALUES (?, ?, ?, ?, ?) RETURNING id";

        try {
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());

            ObjectMapper objectMapper = new ObjectMapper();
            String eventDataJson = objectMapper.writeValueAsString(eventData);

            Integer generatedId = jdbcTemplate.queryForObject(insertQuery, Integer.class, switcherEventMasterId, tenant, eventDataJson, eventTime, createdAt);
            log.info("Created switcher event log with ID: {}", generatedId);
            return generatedId;
        } catch (Exception e) {
            log.error("Error while adding switcher event log", e);
            return null;
        }
    }
}
