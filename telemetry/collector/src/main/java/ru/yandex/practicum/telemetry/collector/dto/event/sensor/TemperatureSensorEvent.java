package ru.yandex.practicum.telemetry.collector.dto.event.sensor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.EventType;
import ru.yandex.practicum.telemetry.collector.mapper.event.SensorEventMapper;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TemperatureSensorEvent extends SensorEvent<TemperatureSensorAvro> {
    private Integer temperatureC;
    private Integer temperatureF;

    @Override
    public EventType getType() {
        return EventType.TEMPERATURE_SENSOR_EVENT;
    }

    @Override
    public TemperatureSensorAvro toAvro(SensorEventMapper mapper) {
        return mapper.toTemperatureSensorAvro(this);
    }
}
