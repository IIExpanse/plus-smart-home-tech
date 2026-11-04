package ru.yandex.practicum.telemetry.aggregator.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public interface AggregationService {
    void processSensorEvent(SensorEventAvro sensorEvent, KafkaProducer<String, SensorsSnapshotAvro> producer, String topic);
}
