package ru.yandex.practicum.telemetry.aggregator.config.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.yandex.practicum.telemetry.aggregator.config.PropertiesProvider;

import java.util.Properties;

@ConfigurationProperties(prefix = "aggregator.kafka.producer.sensor-snapshot")
public record SensorsSnapshotProducerConfig(
        String bootstrapServers,
        String topic,
        String keySerializer,
        String valueSerializer
) implements PropertiesProvider {
    @Override
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        return properties;
    }
}
