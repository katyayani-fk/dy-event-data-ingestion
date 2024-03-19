package com.fourkites.facilities.dy.eventdataingestion.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class SwitcherEventLog {
    
    private Integer id;
    private Integer switcherEventMasterId;
    private String tenant;
    private String eventData;
    private Timestamp eventTime;
    private Timestamp createdAt;
}

