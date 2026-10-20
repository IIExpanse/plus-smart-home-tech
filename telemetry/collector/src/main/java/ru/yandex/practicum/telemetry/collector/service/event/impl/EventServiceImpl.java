package ru.yandex.practicum.telemetry.collector.service.event.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.AvroSerializable;
import ru.yandex.practicum.telemetry.collector.dto.event.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.exception.AvroSerializationException;
import ru.yandex.practicum.telemetry.collector.kafka.producer.KafkaProducer;
import ru.yandex.practicum.telemetry.collector.mapper.event.HubEventMapper;
import ru.yandex.practicum.telemetry.collector.mapper.event.SensorEventMapper;
import ru.yandex.practicum.telemetry.collector.service.event.EventService;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final KafkaProducer<Void, byte[]> kafkaProducer;
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
                log.debug("Sending sensor event: {}", event);
                kafkaProducer.send(sensorEventTopic, AvroSerializable.toAvroBytes(sensorEventAvro, log));
                log.debug("Sensor event sent: {}", event);
                return;
            }
            kafkaProducer.send(sensorEventTopic, AvroSerializable.toAvroBytes(sensorEventAvro));

        } catch (IOException e) {
            log.error("Error while sending sensor event", e);
            throw new AvroSerializationException(e.getMessage(), e);
        }
    }

    @Override
    public void sendHubEvent(HubEvent<?> event) {
        try {
            HubEventAvro hubEventAvro = hubEventMapper.toHubEventAvro(event, event.toAvro(hubEventMapper));
            if (log.isDebugEnabled()) {
                log.debug("Sending hub event: {}", event);
                kafkaProducer.send(hubEventTopic, AvroSerializable.toAvroBytes(hubEventAvro, log));
                log.debug("Sensor hub event sent: {}", event);
            }
            kafkaProducer.send(hubEventTopic, AvroSerializable.toAvroBytes(hubEventAvro));
        } catch (IOException e) {
            log.error("Error while sending hub event", e);
            throw new AvroSerializationException(e.getMessage(), e);
        }
    }
}
