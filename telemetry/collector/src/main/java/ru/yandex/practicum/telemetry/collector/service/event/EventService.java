package ru.yandex.practicum.telemetry.collector.service.event;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.telemetry.collector.dto.event.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.sensor.SensorEvent;

public interface EventService {
    void sendSensorEvent(SensorEvent<?> event);

    void sendSensorEvent(SensorEventProto eventProto);

    void sendHubEvent(HubEvent<?> event);

    void sendHubEvent(HubEventProto eventProto);
}
