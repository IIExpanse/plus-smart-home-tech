package ru.yandex.practicum.telemetry.aggregator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.telemetry.aggregator.service.AggregationService;
import ru.yandex.practicum.telemetry.aggregator.storage.SnapshotStorage;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AggregationServiceImpl implements AggregationService {
    private final SnapshotStorage snapshotStorage;

    @Override
    public void processSensorEvent(SensorEventAvro sensorEvent, KafkaProducer<String, SensorsSnapshotAvro> producer, String topic) {
        if (log.isDebugEnabled()) {
            log.debug("Processing sensor event: {}", sensorEvent);
        }

        SensorsSnapshotAvro sensorsSnapshot;
        if (snapshotStorage.hasSnapshot(sensorEvent.getHubId())) {
            sensorsSnapshot = snapshotStorage.getSnapshot(sensorEvent.getHubId());
            if (log.isDebugEnabled()) {
                log.debug("Found snapshot for hubId: {}, snapshot: {}", sensorEvent.getHubId(), sensorsSnapshot);
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("No snapshot found for hubId: {}, creating new one", sensorEvent.getHubId());
            }
            sensorsSnapshot = new SensorsSnapshotAvro();
            sensorsSnapshot.setHubId(sensorEvent.getHubId());
            sensorsSnapshot.setTimestamp(sensorEvent.getTimestamp());
            sensorsSnapshot.setSensorsState(new HashMap<>());
        }

        Map<String, SensorStateAvro> sensorsState = sensorsSnapshot.getSensorsState();
        SensorStateAvro oldState = sensorsState.get(sensorEvent.getId());
        SensorStateAvro newState = new SensorStateAvro(sensorEvent.getTimestamp(), sensorEvent.getPayload());

        if (!mustUpdate(oldState, newState, sensorEvent.getId())) {
            if (log.isDebugEnabled()) {
                log.debug("No need to update snapshot for hubId: {}, sensorId: {}",
                        sensorEvent.getHubId(), sensorEvent.getId()
                );
            }
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("Updating snapshot for hubId: {}", sensorEvent.getHubId());
        }

        sensorsState.put(sensorEvent.getId(), newState);
        sensorsSnapshot.setTimestamp(sensorEvent.getTimestamp());

        snapshotStorage.saveSnapshot(sensorsSnapshot.getHubId(), sensorsSnapshot);
        if (log.isDebugEnabled()) {
            log.debug("Saved snapshot for hubId: {}", sensorEvent.getHubId());
        }

        ProducerRecord<String, SensorsSnapshotAvro> producerRecord = new ProducerRecord<>(topic, sensorEvent.getHubId(), sensorsSnapshot);
        producer.send(producerRecord);
        if (log.isDebugEnabled()) {
            log.debug("Sent snapshot for hubId: {}, snapshot: {}", sensorEvent.getHubId(), sensorsSnapshot);
        }
    }

    private boolean mustUpdate(SensorStateAvro oldState, SensorStateAvro newState, String sensorId) {
        if (oldState == null) {
            if (log.isDebugEnabled()) {
                log.debug("No old state found for sensorId: {}, creating new one", sensorId);
            }
            return true;
        }

        if (oldState.getTimestamp().isBefore(newState.getTimestamp())) {
            if (log.isDebugEnabled()) {
                log.debug("Old state is older than new state for sensorId: {}, updating", sensorId);
            }
            return true;
        }
        boolean isEqual = oldState.getData().equals(newState.getData());
        if (log.isDebugEnabled()) {
            log.debug("Old state is {} to new state for sensorId: {}, oldState: {}, newState: {}",
                    isEqual ? "equal" : "not equal", sensorId, oldState.getData(), newState.getData());
        }

        return !isEqual;
    }
}
