package ru.yandex.practicum.telemetry.collector.controller.event.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.telemetry.collector.dto.event.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.service.event.EventService;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class HttpEventController {
    private final EventService eventService;

    @PostMapping("/sensors")
    public ResponseEntity<Void> collectSensorEvent(@RequestBody SensorEvent<?> event) {
        if (log.isDebugEnabled()) {
            log.debug("Received http sensor event: {}", event);
        }
        eventService.sendSensorEvent(event);
        if (log.isDebugEnabled()) {
            log.debug("Successfully processed http sensor event: {}", event);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/hubs")
    public ResponseEntity<Void> collectHubEvent(@RequestBody HubEvent<?> event) {
        if (log.isDebugEnabled()) {
            log.debug("Received http hub event: {}", event);
        }
        eventService.sendHubEvent(event);
        if (log.isDebugEnabled()) {
            log.debug("Successfully processed http hub event: {}", event);
        }
        return ResponseEntity.ok().build();
    }
}
