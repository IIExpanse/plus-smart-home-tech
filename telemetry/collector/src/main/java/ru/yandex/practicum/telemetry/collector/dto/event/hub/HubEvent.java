package ru.yandex.practicum.telemetry.collector.dto.event.hub;

import org.apache.avro.specific.SpecificRecord;
import ru.yandex.practicum.telemetry.collector.dto.event.AvroSerializable;
import ru.yandex.practicum.telemetry.collector.dto.event.GenericEvent;
import ru.yandex.practicum.telemetry.collector.mapper.event.HubEventMapper;

public abstract class HubEvent<T extends SpecificRecord> extends GenericEvent
        implements HubEventDiscriminator, AvroSerializable<T, HubEventMapper> {
}
