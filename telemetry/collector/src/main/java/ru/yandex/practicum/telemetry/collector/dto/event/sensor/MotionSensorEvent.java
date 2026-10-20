package ru.yandex.practicum.telemetry.collector.dto.event.sensor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.EventType;
import ru.yandex.practicum.telemetry.collector.mapper.event.SensorEventMapper;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MotionSensorEvent extends SensorEvent<MotionSensorAvro> {
    private Integer linkQuality;
    private Boolean motion;
    private Integer voltage;

    @Override
    public EventType getType() {
        return EventType.MOTION_SENSOR_EVENT;
    }

    @Override
    public MotionSensorAvro toAvro(SensorEventMapper mapper) {
        return mapper.toMotionSensorAvro(this);
    }
}
