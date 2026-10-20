package ru.yandex.practicum.telemetry.collector.dto.event.hub;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.EventType;
import ru.yandex.practicum.telemetry.collector.mapper.event.HubEventMapper;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DeviceRemovedEvent extends HubEvent<DeviceRemovedEventAvro> {

    @Override
    public EventType getType() {
        return EventType.DEVICE_REMOVED;
    }

    @Override
    public DeviceRemovedEventAvro toAvro(HubEventMapper mapper) {
        return mapper.toDeviceRemovedEventAvro(this);
    }
}
