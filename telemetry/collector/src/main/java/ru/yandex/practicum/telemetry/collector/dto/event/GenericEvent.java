package ru.yandex.practicum.telemetry.collector.dto.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class GenericEvent {
    protected String id;
    protected String hubId;
    protected Instant timestamp = Instant.now();

    public abstract EventType getType();
}