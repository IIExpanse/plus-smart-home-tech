package ru.yandex.practicum.telemetry.collector.kafka.producer.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.telemetry.collector.kafka.producer.KafkaProducerBase;

@Component
public class BytesValueNullKeyProducer extends KafkaProducerBase<Void, byte[]> {
    public BytesValueNullKeyProducer(KafkaTemplate<Void, byte[]> kafkaTemplate) {
        super(kafkaTemplate);
    }
}