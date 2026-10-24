package ru.yandex.practicum.telemetry.collector.mapper.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.telemetry.collector.dto.event.hub.DeviceAction;
import ru.yandex.practicum.telemetry.collector.dto.event.hub.DeviceAddedEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.hub.DeviceRemovedEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.hub.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.collector.dto.event.hub.ScenarioCondition;
import ru.yandex.practicum.telemetry.collector.dto.event.hub.ScenarioRemovedEvent;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HubEventMapper {
    HubEventAvro toHubEventAvro(HubEvent<?> hubEvent, Object payload);

    DeviceActionAvro toDeviceActionAvro(DeviceAction deviceAction);

    @Mapping(target = "type", source = "deviceType")
    DeviceAddedEventAvro toDeviceAddedEventAvro(DeviceAddedEvent deviceAddedEvent);

    DeviceRemovedEventAvro toDeviceRemovedEventAvro(DeviceRemovedEvent deviceRemoved);

    ScenarioAddedEventAvro toScenarioAddedEventAvro(ScenarioAddedEvent scenarioAdded);

    ScenarioConditionAvro toScenarioConditionAvro(ScenarioCondition scenarioCondition);

    ScenarioRemovedEventAvro toScenarioRemovedEventAvro(ScenarioRemovedEvent scenarioRemovedEvent);
}
