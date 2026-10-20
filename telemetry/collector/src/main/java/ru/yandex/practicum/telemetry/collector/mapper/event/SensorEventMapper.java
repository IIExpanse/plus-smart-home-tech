package ru.yandex.practicum.telemetry.collector.mapper.event;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.sensor.ClimateSensorEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.sensor.LightSensorEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.sensor.MotionSensorEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.sensor.SwitchSensorEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.sensor.TemperatureSensorEvent;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SensorEventMapper {
    SensorEventAvro toSensorEventAvro(SensorEvent<?> sensorEvent, Object payload);

    ClimateSensorAvro toClimateSensorAvro(ClimateSensorEvent event);

    LightSensorAvro toLightSensorAvro(LightSensorEvent event);

    MotionSensorAvro toMotionSensorAvro(MotionSensorEvent event);

    SwitchSensorAvro toSwitchSensorAvro(SwitchSensorEvent event);

    TemperatureSensorAvro toTemperatureSensorAvro(TemperatureSensorEvent event);
}
