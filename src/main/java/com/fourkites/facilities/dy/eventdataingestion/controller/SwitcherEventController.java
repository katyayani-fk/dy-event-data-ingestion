package com.fourkites.facilities.dy.eventdataingestion.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.Nullable;

import com.fourkites.facilities.dy.eventdataingestion.service.SwitcherEventService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/switcher_event")
public class SwitcherEventController {

    @NonNull
    private SwitcherEventService eventService;

    @GetMapping
    public String greet(){
        log.info("Someone called me and I am awake");
        return "Success";
    }
    @PostMapping
    public ResponseEntity<String> createSwitcherEventMaster(
            @NonNull @RequestParam String tenant,
            @Nullable @RequestParam(value = "site_id") Integer siteId,
            @NonNull @RequestHeader(value = "request_id") String requestId,
            @NonNull @RequestHeader(value = "event") String event,
            @Nullable @RequestHeader(value = "sub_event") String subEvent,
            @NonNull @RequestHeader(value = "event_time") String eventTimeStr,
            @RequestBody Map<String, Object> requestBody) {

        try {
            MDC.put("request_id",requestId);

            Integer userId = (Integer) requestBody.get("user_id");
            Timestamp eventTime = Timestamp.valueOf(eventTimeStr);
            Map<String, Object> eventData = new HashMap<>();
            eventData.put("request_id", requestId);
            eventData.put("event", event);
            eventData.put("sub_event", subEvent);
            eventData.put("event_time", eventTimeStr);
            eventData.put("user_id", userId);
            eventData.putAll(requestBody);
            eventService.addSwitcherEvent(tenant, siteId, eventTime, userId, eventData);
            log.info("Switcher Event created successfully for Request ID: {}", requestId);
            return ResponseEntity.ok("Switcher Event created successfully!");
        } catch (RuntimeException e) {
            log.error("Error while processing request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Internal server error occurred");
        }
    }
}
