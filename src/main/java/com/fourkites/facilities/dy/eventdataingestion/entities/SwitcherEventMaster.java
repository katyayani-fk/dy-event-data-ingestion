package com.fourkites.facilities.dy.eventdataingestion.entities;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
public class SwitcherEventMaster {

    private Integer id;
    private String tenant;
    private Integer userId;
    private Integer siteId;
    private Date eventDate;
    private Timestamp createdAt;
}
