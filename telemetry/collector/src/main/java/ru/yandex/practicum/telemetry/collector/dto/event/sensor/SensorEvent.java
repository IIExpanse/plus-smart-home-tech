package ru.yandex.practicum.telemetry.collector.dto.event.sensor;

import org.apache.avro.specific.SpecificRecord;
import ru.yandex.practicum.telemetry.collector.dto.event.AvroSerializable;
import ru.yandex.practicum.telemetry.collector.dto.event.GenericEvent;
import ru.yandex.practicum.telemetry.collector.mapper.event.SensorEventMapper;

public abstract class SensorEvent<T extends SpecificRecord> extends GenericEvent
        implements SensorEventDiscriminator, AvroSerializable<T, SensorEventMapper> {
}
