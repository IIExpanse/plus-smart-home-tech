package ru.yandex.practicum.telemetry.collector.controller.event;

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
public class EventController {
    private final EventService eventService;

    @PostMapping("/sensors")
    public ResponseEntity<Void> collectSensorEvent(@RequestBody SensorEvent<?> event) {
        if (log.isDebugEnabled()) {
            log.debug("Received sensor event: {}", event);
        }
        eventService.sendSensorEvent(event);
        if (log.isDebugEnabled()) {
            log.debug("Successfully processed sensor event: {}", event);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/hubs")
    public ResponseEntity<Void> collectHubEvent(@RequestBody HubEvent<?> event) {
        if (log.isDebugEnabled()) {
            log.debug("Received hub event: {}", event);
        }
        eventService.sendHubEvent(event);
        if (log.isDebugEnabled()) {
            log.debug("Successfully processed hub event: {}", event);
        }
        return ResponseEntity.ok().build();
    }
}
