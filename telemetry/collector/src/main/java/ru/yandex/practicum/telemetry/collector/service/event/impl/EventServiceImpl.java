package ru.yandex.practicum.telemetry.collector.service.event.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.AvroSerializable;
import ru.yandex.practicum.telemetry.collector.dto.event.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.exception.AvroSerializationException;
import ru.yandex.practicum.telemetry.collector.kafka.producer.Producer;
import ru.yandex.practicum.telemetry.collector.mapper.event.HubEventMapper;
import ru.yandex.practicum.telemetry.collector.mapper.event.SensorEventMapper;
import ru.yandex.practicum.telemetry.collector.service.event.EventService;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final Producer<Void, byte[]> producer;
    private final SensorEventMapper sensorEventMapper;
    private final HubEventMapper hubEventMapper;
    @Value("${collector.kafka.topic.sensor-event}")
    private String sensorEventTopic;
    @Value("${collector.kafka.topic.hub-event}")
    private String hubEventTopic;

    @Override
    public void sendSensorEvent(SensorEvent<?> event) {
        try {
            SensorEventAvro sensorEventAvro = sensorEventMapper.toSensorEventAvro(event, event.toAvro(sensorEventMapper));
            if (log.isDebugEnabled()) {
                log.debug("Sending sensor event from http: {}", event);
                producer.send(sensorEventTopic, AvroSerializable.toAvroBytes(sensorEventAvro, log));
                log.debug("Sensor event sent from http: {}", event);
                return;
            }
            producer.send(sensorEventTopic, AvroSerializable.toAvroBytes(sensorEventAvro));

        } catch (IOException e) {
            log.error("Error while sending sensor event from http", e);
            throw new AvroSerializationException(e.getMessage(), e);
        }
    }

    @Override
    public void sendSensorEvent(SensorEventProto eventProto) {
        try {
            SensorEventAvro sensorEventAvro = sensorEventMapper.toSensorEventAvro(eventProto);
            if (log.isDebugEnabled()) {
                log.debug("Sending sensor event from gRPC: {}", eventProto);
                producer.send(sensorEventTopic, AvroSerializable.toAvroBytes(sensorEventAvro, log));
                log.debug("Sensor event sent from gRPC: {}", eventProto);
                return;
            }
            producer.send(sensorEventTopic, AvroSerializable.toAvroBytes(sensorEventAvro));

        } catch (IOException e) {
            log.error("Error while sending sensor event from gRPC", e);
            throw new AvroSerializationException(e.getMessage(), e);
        }
    }

    @Override
    public void sendHubEvent(HubEvent<?> event) {
        try {
            HubEventAvro hubEventAvro = hubEventMapper.toHubEventAvro(event, event.toAvro(hubEventMapper));
            if (log.isDebugEnabled()) {
                log.debug("Sending hub event from http: {}", event);
                producer.send(hubEventTopic, AvroSerializable.toAvroBytes(hubEventAvro, log));
                log.debug("Sensor hub event sent from http: {}", event);
                return;
            }
            producer.send(hubEventTopic, AvroSerializable.toAvroBytes(hubEventAvro));
        } catch (IOException e) {
            log.error("Error while sending hub event from http", e);
            throw new AvroSerializationException(e.getMessage(), e);
        }
    }

    @Override
    public void sendHubEvent(HubEventProto eventProto) {
        try {
            HubEventAvro hubEventAvro = hubEventMapper.toHubEventAvro(eventProto);
            if (log.isDebugEnabled()) {
                log.debug("Sending hub event from gRPC: {}", eventProto);
                producer.send(hubEventTopic, AvroSerializable.toAvroBytes(hubEventAvro, log));
                log.debug("Sensor hub event sent from gRPC: {}", eventProto);
                return;
            }
            producer.send(hubEventTopic, AvroSerializable.toAvroBytes(hubEventAvro));
        } catch (IOException e) {
            log.error("Error while sending hub event from gRPC", e);
            throw new AvroSerializationException(e.getMessage(), e);
        }
    }
}
