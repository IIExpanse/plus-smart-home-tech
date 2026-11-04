package ru.yandex.practicum.telemetry.aggregator.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.yandex.practicum.telemetry.aggregator.config.PropertiesProvider;

import java.util.Properties;

@ConfigurationProperties(prefix = "aggregator.kafka.consumer.sensor-event")
public record SensorEventConsumerConfig(
        String bootstrapServers,
        String groupId,
        String topic,
        String keyDeserializer,
        String valueDeserializer
) implements PropertiesProvider {
    @Override
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        return properties;
    }
}
