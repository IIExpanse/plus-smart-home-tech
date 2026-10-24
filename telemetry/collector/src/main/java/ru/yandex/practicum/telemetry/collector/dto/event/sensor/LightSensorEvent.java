package ru.yandex.practicum.telemetry.collector.dto.event.sensor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.EventType;
import ru.yandex.practicum.telemetry.collector.mapper.event.SensorEventMapper;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LightSensorEvent extends SensorEvent<LightSensorAvro> {
    private Integer linkQuality;
    private Integer luminosity;

    @Override
    public EventType getType() {
        return EventType.LIGHT_SENSOR_EVENT;
    }

    @Override
    public LightSensorAvro toAvro(SensorEventMapper mapper) {
        return mapper.toLightSensorAvro(this);
    }
}
