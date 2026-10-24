package ru.yandex.practicum.telemetry.collector.dto.event.sensor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.EventType;
import ru.yandex.practicum.telemetry.collector.mapper.event.SensorEventMapper;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ClimateSensorEvent extends SensorEvent<ClimateSensorAvro> {
    private Integer temperatureC;
    private Integer humidity;
    private Integer co2Level;

    @Override
    public EventType getType() {
        return EventType.CLIMATE_SENSOR_EVENT;
    }

    @Override
    public ClimateSensorAvro toAvro(SensorEventMapper mapper) {
        return mapper.toClimateSensorAvro(this);
    }
}
