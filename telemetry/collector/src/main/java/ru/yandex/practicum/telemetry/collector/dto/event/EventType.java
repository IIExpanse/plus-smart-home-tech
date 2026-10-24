package ru.yandex.practicum.telemetry.collector.dto.event;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EventType {
    MOTION_SENSOR_EVENT,
    TEMPERATURE_SENSOR_EVENT,
    LIGHT_SENSOR_EVENT,
    CLIMATE_SENSOR_EVENT,
    SWITCH_SENSOR_EVENT,
    DEVICE_ADDED,
    DEVICE_REMOVED,
    SCENARIO_ADDED,
    SCENARIO_REMOVED
}
