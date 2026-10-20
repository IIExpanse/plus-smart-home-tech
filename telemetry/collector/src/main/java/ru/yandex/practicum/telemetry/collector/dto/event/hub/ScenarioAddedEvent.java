package ru.yandex.practicum.telemetry.collector.dto.event.hub;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.EventType;
import ru.yandex.practicum.telemetry.collector.mapper.event.HubEventMapper;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ScenarioAddedEvent extends HubEvent<ScenarioAddedEventAvro> {
    private String name;
    private List<ScenarioCondition> conditions;
    private List<DeviceAction> actions;

    @Override
    public EventType getType() {
        return EventType.SCENARIO_ADDED;
    }

    @Override
    public ScenarioAddedEventAvro toAvro(HubEventMapper mapper) {
        return mapper.toScenarioAddedEventAvro(this);
    }
}
