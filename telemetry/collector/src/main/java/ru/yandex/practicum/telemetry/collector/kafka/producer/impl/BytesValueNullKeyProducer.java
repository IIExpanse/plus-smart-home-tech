package ru.yandex.practicum.telemetry.collector.kafka.producer.impl;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.telemetry.collector.kafka.producer.ProducerBase;

@Component
public class BytesValueNullKeyProducer extends ProducerBase<Void, byte[]> {
    public BytesValueNullKeyProducer(KafkaProducer<Void, byte[]> kafkaProducer) {
        super(kafkaProducer);
    }
}