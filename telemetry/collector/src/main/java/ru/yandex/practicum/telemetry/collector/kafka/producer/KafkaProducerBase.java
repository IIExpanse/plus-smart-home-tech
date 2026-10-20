package ru.yandex.practicum.telemetry.collector.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public abstract class KafkaProducerBase<K,V> implements KafkaProducer<K,V> {
    protected final KafkaTemplate<K, V> kafkaTemplate;

    @Override
    public void send(String topic, V value) {
        kafkaTemplate.send(topic, value);
    }
}
