package ru.yandex.practicum.telemetry.aggregator.storage.inmemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.telemetry.aggregator.storage.SnapshotStorage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InmemorySnapshotStorage implements SnapshotStorage {
    private final Map<String, SensorsSnapshotAvro> snapshotsMap = new ConcurrentHashMap<>();

    @Override
    public boolean hasSnapshot(String hubId) {
        return snapshotsMap.containsKey(hubId);
    }

    @Override
    public void saveSnapshot(String hubId, SensorsSnapshotAvro snapshot) {
        if (hubId == null || snapshot == null) {
            throw new IllegalArgumentException("Hub ID and snapshot must not be null");
        }
        snapshotsMap.put(hubId, snapshot);
    }

    @Override
    public SensorsSnapshotAvro getSnapshot(String hubId) {
        return snapshotsMap.get(hubId);
    }
}
