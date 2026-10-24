package ru.yandex.practicum.telemetry.collector.dto.event.hub;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.EventType;
import ru.yandex.practicum.telemetry.collector.mapper.event.HubEventMapper;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DeviceAddedEvent extends HubEvent<DeviceAddedEventAvro> {
    private DeviceType deviceType;

    @Override
    public EventType getType() {
        return EventType.DEVICE_ADDED;
    }

    @Override
    public DeviceAddedEventAvro toAvro(HubEventMapper mapper) {
        return mapper.toDeviceAddedEventAvro(this);
    }

    public enum DeviceType {
        MOTION_SENSOR,
        TEMPERATURE_SENSOR,
        LIGHT_SENSOR,
        CLIMATE_SENSOR,
        SWITCH_SENSOR
    }
}
