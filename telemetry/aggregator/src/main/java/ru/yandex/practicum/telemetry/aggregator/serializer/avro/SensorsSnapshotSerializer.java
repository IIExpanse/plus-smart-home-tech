package ru.yandex.practicum.telemetry.aggregator.serializer.avro;

import org.apache.avro.specific.SpecificDatumWriter;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public class SensorsSnapshotSerializer extends BaseAvroSerializer<SensorsSnapshotAvro> {
    public SensorsSnapshotSerializer() {
        super(new SpecificDatumWriter<>(SensorsSnapshotAvro.getClassSchema()));
    }
}
