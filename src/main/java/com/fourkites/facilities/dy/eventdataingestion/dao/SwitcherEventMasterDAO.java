package com.fourkites.facilities.dy.eventdataingestion.dao;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SwitcherEventMasterDAO {
    private final JdbcTemplate jdbcTemplate;

    public Integer add(String tenant, Integer siteId, Timestamp eventTime, Integer userId) {
        Date eventDate = new Date(eventTime.getTime());
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());

        String insertQuery = "INSERT INTO switcher_event_master (user_id, site_id, event_date, created_at, tenant) "
                + "VALUES (?, ?, ?, ?, ?) "
                + "RETURNING id";

        try {
            Integer generatedId = jdbcTemplate.queryForObject(insertQuery, Integer.class, userId, siteId, eventDate,
                    createdAt, tenant);
            log.info("Created record in switcher event master with ID: {}", generatedId);
            return generatedId;
        } catch (DuplicateKeyException e) {
            String selectQuery = "SELECT id FROM switcher_event_master WHERE user_id = ? AND event_date = ? AND tenant = ?";
            Integer existingId = jdbcTemplate.queryForObject(selectQuery, Integer.class, userId, eventDate, tenant);
            log.info("Record for the switcher for the day already exists in switcher_event_master with ID: {}", existingId);
            return existingId;
        } catch (Exception e) {
            log.error("Error while adding record in switcher event master", e);
            return null;
        }
    }
}
