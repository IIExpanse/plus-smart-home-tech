package ru.yandex.practicum.telemetry.collector.dto.event.hub;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ScenarioCondition {
    private String sensorId;
    private ConditionType type;
    private OperationType operation;
    private Integer value;

    public enum ConditionType {
        MOTION,
        LUMINOSITY,
        SWITCH,
        TEMPERATURE,
        CO2LEVEL,
        HUMIDITY
    }

    public enum OperationType {
        EQUALS,
        GREATER_THAN,
        LOWER_THAN
    }
}
