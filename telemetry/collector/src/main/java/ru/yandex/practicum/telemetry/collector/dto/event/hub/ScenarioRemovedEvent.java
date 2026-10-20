package ru.yandex.practicum.telemetry.collector.dto.event.hub;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.EventType;
import ru.yandex.practicum.telemetry.collector.mapper.event.HubEventMapper;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ScenarioRemovedEvent extends HubEvent<ScenarioRemovedEventAvro> {
    private String name;

    @Override
    public EventType getType() {
        return EventType.SCENARIO_REMOVED;
    }

    @Override
    public ScenarioRemovedEventAvro toAvro(HubEventMapper mapper) {
        return mapper.toScenarioRemovedEventAvro(this);
    }
}
