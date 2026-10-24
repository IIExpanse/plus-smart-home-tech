package ru.yandex.practicum.telemetry.collector.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.yandex.practicum.telemetry.collector.exception.KafkaProducerException;

@Slf4j
@RequiredArgsConstructor
public abstract class ProducerBase<K, V> implements Producer<K, V> {
    protected final KafkaProducer<K, V> kafkaProducer;

    @Override
    public void send(String topic, V value) {
        ProducerRecord<K, V> producerRecord = new ProducerRecord<>(topic, value);

        try {
            kafkaProducer.send(producerRecord, (metadata, exception) -> {
                if (exception == null) {
                    if (log.isDebugEnabled()) {
                        log.debug("Record sent successfully: {}", metadata);
                    }
                } else {
                    log.error("Error sending record: ", exception);
                }
            });
            kafkaProducer.flush();
        } catch (Exception e) {
            log.error("Error while waiting for record to be sent: ", e);
            throw new KafkaProducerException(e.getMessage(), e);
        }
    }
}
