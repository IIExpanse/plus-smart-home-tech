package ru.yandex.practicum.telemetry.collector.mapper.event;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.grpc.telemetry.event.ClimateSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.LightSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.TemperatureSensorProto;
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
import ru.yandex.practicum.telemetry.collector.mapper.BaseTypesMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BaseTypesMapper.class)
public interface SensorEventMapper {
    SensorEventAvro toSensorEventAvro(SensorEvent<?> sensorEvent, Object payload);

    SensorEventAvro toSensorEventAvro(SensorEventProto sensorEvent, Object payload);

    default SensorEventAvro toSensorEventAvro(SensorEventProto sensorEventProto) {
        return switch (sensorEventProto.getPayloadCase().getNumber()) {
            case 4 -> this.toSensorEventAvro(sensorEventProto, this.toMotionSensorAvro(sensorEventProto.getMotionSensor()));

            case 5 -> this.toSensorEventAvro(sensorEventProto, this.toTemperatureSensorAvro(sensorEventProto.getTemperatureSensor()));

            case 6 -> this.toSensorEventAvro(sensorEventProto, this.toLightSensorAvro(sensorEventProto.getLightSensor()));

            case 7 -> this.toSensorEventAvro(sensorEventProto, this.toClimateSensorAvro(sensorEventProto.getClimateSensor()));

            case 8 -> this.toSensorEventAvro(sensorEventProto, this.toSwitchSensorAvro(sensorEventProto.getSwitchSensor()));

            default ->
                    throw new IllegalArgumentException("Unknown payload case number: " + sensorEventProto.getPayloadCase());
        };
    }

    ClimateSensorAvro toClimateSensorAvro(ClimateSensorEvent event);

    ClimateSensorAvro toClimateSensorAvro(ClimateSensorProto climateSensorProto);

    LightSensorAvro toLightSensorAvro(LightSensorEvent event);

    LightSensorAvro toLightSensorAvro(LightSensorProto lightSensorProto);

    MotionSensorAvro toMotionSensorAvro(MotionSensorEvent event);

    MotionSensorAvro toMotionSensorAvro(MotionSensorProto motionSensorProto);

    SwitchSensorAvro toSwitchSensorAvro(SwitchSensorEvent event);

    SwitchSensorAvro toSwitchSensorAvro(SwitchSensorProto switchSensorProto);

    TemperatureSensorAvro toTemperatureSensorAvro(TemperatureSensorEvent event);

    TemperatureSensorAvro toTemperatureSensorAvro(TemperatureSensorProto temperatureSensorProto);
}
