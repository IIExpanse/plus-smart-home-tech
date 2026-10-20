package ru.yandex.practicum.telemetry.collector.service.event;

import ru.yandex.practicum.telemetry.collector.dto.event.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.sensor.SensorEvent;

public interface EventService {
    void sendSensorEvent(SensorEvent<?> event);

    void sendHubEvent(HubEvent<?> event);
}
