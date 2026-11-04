package ru.yandex.practicum.telemetry.aggregator.storage;

import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public interface SnapshotStorage {
    boolean hasSnapshot(String hubId);

    void saveSnapshot(String hubId, SensorsSnapshotAvro snapshot);

    SensorsSnapshotAvro getSnapshot(String hubId);
}
