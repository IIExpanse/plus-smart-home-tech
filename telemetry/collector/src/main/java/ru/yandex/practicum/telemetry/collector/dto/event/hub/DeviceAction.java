package ru.yandex.practicum.telemetry.collector.dto.event.hub;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DeviceAction {
    private String sensorId;
    private ActionType type;
    private Integer value;

    public enum ActionType {
        ACTIVATE,
        DEACTIVATE,
        INVERSE,
        SET_VALUE
    }
}
