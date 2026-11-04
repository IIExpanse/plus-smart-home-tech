package ru.yandex.practicum.telemetry.aggregator.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.telemetry.aggregator.config.kafka.SensorEventConsumerConfig;
import ru.yandex.practicum.telemetry.aggregator.config.kafka.SensorsSnapshotProducerConfig;
import ru.yandex.practicum.telemetry.aggregator.service.AggregationService;

import java.io.Closeable;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.StreamSupport;

/**
 * Класс AggregationStarter, ответственный за запуск агрегации данных.
 */
@Slf4j
@Component
public class AggregationStarter implements Closeable {
    private final AggregationService aggregationService;
    private final KafkaConsumer<String, SensorEventAvro> consumer;
    private final KafkaProducer<String, SensorsSnapshotAvro> producer;
    private final String consumerTopic;
    private final String producerTopic;
    private final Duration pollTimeout;
    private final AtomicBoolean isRunning;

    public AggregationStarter(
            @Autowired AggregationService aggregationService,
            @Autowired SensorEventConsumerConfig sensorEventConsumerConfig,
            @Autowired SensorsSnapshotProducerConfig sensorsSnapshotProducerConfig,
            @Value("${aggregator.kafka.consumer.poll-timeout-ms}") long pollTimeoutMs
    ) {
        this.aggregationService = aggregationService;
        this.consumer = new KafkaConsumer<>(sensorEventConsumerConfig.getProperties());
        this.producer = new KafkaProducer<>(sensorsSnapshotProducerConfig.getProperties());
        this.consumerTopic = sensorEventConsumerConfig.topic();
        this.producerTopic = sensorsSnapshotProducerConfig.topic();
        this.pollTimeout = Duration.ofMillis(pollTimeoutMs);
        this.isRunning = new AtomicBoolean(true);
    }

    /**
     * Метод для начала процесса агрегации данных.
     * Подписывается на топики для получения событий от датчиков,
     * формирует снимок их состояния и записывает в кафку.
     */
    public void start() {
        try {
            log.info("Starting aggregation");
            log.info("Subscribing to topic: {}", consumerTopic);
            consumer.subscribe(List.of(consumerTopic));

            while (this.isRunning.get()) {
                if (log.isTraceEnabled()) {
                    log.trace("Polling for records");
                }
                ConsumerRecords<String, SensorEventAvro> consumerRecords = consumer.poll(pollTimeout);
                if (log.isTraceEnabled()) {
                    log.trace("Polled {} records", consumerRecords.count());
                }

                if (!consumerRecords.isEmpty()) {
                    StreamSupport.stream(consumerRecords.spliterator(), false)
                            .forEach(consumerRecord -> aggregationService
                                    .processSensorEvent(consumerRecord.value(), producer, producerTopic)
                            );
                    consumer.commitSync();
                }
            }

        } catch (WakeupException ignored) {
            // ignored
        } catch (Exception e) {
            log.error("Error while processing sensor events", e);
        } finally {
            shutdown();
        }
    }

    private void shutdown() {
        try {
            consumer.commitSync();
            consumer.unsubscribe();
            producer.flush();
        } finally {
            log.info("Closing consumer");
            consumer.close();
            log.info("Closing producer");
            producer.close();
        }
    }

    @Override
    public void close() {
        this.isRunning.set(false);
        this.consumer.wakeup();
    }
}
