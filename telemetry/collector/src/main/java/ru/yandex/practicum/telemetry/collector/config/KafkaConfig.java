package ru.yandex.practicum.telemetry.collector.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfig {
    @Value("${collector.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${collector.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${collector.kafka.producer.value-serializer}")
    private String valueSerializer;
    @Value("${collector.kafka.producer.retries}")
    private int retries;

    @Bean
    public KafkaProducer<Void, byte[]> kafkaProducer() {
        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        properties.put(ProducerConfig.RETRIES_CONFIG, retries);

        return new KafkaProducer<>(properties);
    }
}
