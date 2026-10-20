package ru.yandex.practicum.telemetry.collector.kafka.producer;

public interface KafkaProducer<K, V> {
    default void send(String topic, K key, V value) {
        send(topic, value);
    }

    void send(String topic, V value);
}