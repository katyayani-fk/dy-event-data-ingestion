package com.fourkites.facilities.dy.eventdataingestion.service;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fourkites.facilities.dy.eventdataingestion.dao.SwitcherEventLogDAO;
import com.fourkites.facilities.dy.eventdataingestion.dao.SwitcherEventMasterDAO;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SwitcherEventService {

    @NonNull
    private final SwitcherEventMasterDAO switcherEventMasterDAO;
    
    @NonNull
    private final SwitcherEventLogDAO switcherEventLogDAO;

    public void addSwitcherEvent(String tenant, Integer siteId, Timestamp eventTime, Integer userId, Map<String, Object> eventData) {
        Integer switcherEventMasterId = switcherEventMasterDAO.add(tenant, siteId, eventTime, userId);
        if (switcherEventMasterId != null) {
            switcherEventLogDAO.add(switcherEventMasterId, tenant, eventData, eventTime);
        } else {
            throw new RuntimeException("Failed to add switcher event master record");
        }
    }
}