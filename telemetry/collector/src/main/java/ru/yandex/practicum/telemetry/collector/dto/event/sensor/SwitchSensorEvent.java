package ru.yandex.practicum.telemetry.collector.dto.event.sensor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.EventType;
import ru.yandex.practicum.telemetry.collector.mapper.event.SensorEventMapper;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SwitchSensorEvent extends SensorEvent<SwitchSensorAvro> {
    private Boolean state;

    @Override
    public EventType getType() {
        return EventType.SWITCH_SENSOR_EVENT;
    }

    @Override
    public SwitchSensorAvro toAvro(SensorEventMapper mapper) {
        return mapper.toSwitchSensorAvro(this);
    }
}
