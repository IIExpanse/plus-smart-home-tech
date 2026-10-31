package ru.yandex.practicum.telemetry.collector.mapper.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import ru.yandex.practicum.grpc.telemetry.event.ConditionOperationProto;
import ru.yandex.practicum.grpc.telemetry.event.ConditionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceRemovedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioRemovedEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
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
import ru.yandex.practicum.telemetry.collector.mapper.BaseTypesMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BaseTypesMapper.class)
public interface HubEventMapper {
    HubEventAvro toHubEventAvro(HubEvent<?> hubEvent, Object payload);

    HubEventAvro toHubEventAvro(HubEventProto hubEventProto, Object payload);

    default HubEventAvro toHubEventAvro(HubEventProto hubEventProto) {
        return switch (hubEventProto.getPayloadCase().getNumber()) {
            case 3 -> this.toHubEventAvro(hubEventProto, this.toDeviceAddedEventAvro(hubEventProto.getDeviceAdded()));

            case 4 ->
                    this.toHubEventAvro(hubEventProto, this.toDeviceRemovedEventAvro(hubEventProto.getDeviceRemoved()));

            case 5 ->
                    this.toHubEventAvro(hubEventProto, this.toScenarioAddedEventAvro(hubEventProto.getScenarioAdded()));

            case 6 ->
                    this.toHubEventAvro(hubEventProto, this.toScenarioRemovedEventAvro(hubEventProto.getScenarioRemoved()));

            default ->
                    throw new IllegalArgumentException("Unknown payload case number: " + hubEventProto.getPayloadCase());
        };
    }

    DeviceActionAvro toDeviceActionAvro(DeviceAction deviceAction);

    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL)
    DeviceActionAvro toDeviceActionAvro(DeviceActionProto deviceActionProto);

    @Mapping(source = "deviceType", target = "type")
    DeviceAddedEventAvro toDeviceAddedEventAvro(DeviceAddedEvent deviceAddedEvent);

    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL)
    DeviceAddedEventAvro toDeviceAddedEventAvro(DeviceAddedEventProto deviceAddedEventProto);

    DeviceRemovedEventAvro toDeviceRemovedEventAvro(DeviceRemovedEvent deviceRemoved);

    DeviceRemovedEventAvro toDeviceRemovedEventAvro(DeviceRemovedEventProto deviceRemovedEventProto);

    ScenarioAddedEventAvro toScenarioAddedEventAvro(ScenarioAddedEvent scenarioAdded);

    @Mapping(target = "actions", expression = "java(scenarioAddedEventProto.getActionsList().stream().map(this::toDeviceActionAvro).toList())")
    @Mapping(target = "conditions", expression = "java(scenarioAddedEventProto.getConditionsList().stream().map(this::toScenarioConditionAvro).toList())")
    ScenarioAddedEventAvro toScenarioAddedEventAvro(ScenarioAddedEventProto scenarioAddedEventProto);

    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL)
    ConditionTypeAvro toConditionTypeAvro(ConditionTypeProto conditionTypeProto);

    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL)
    ConditionOperationAvro toConditionOperationAvro(ConditionOperationProto conditionOperationProto);

    ScenarioConditionAvro toScenarioConditionAvro(ScenarioCondition scenarioCondition);

    @Mapping(target = "value", expression = "java(scenarioConditionProto.hasBoolValue() ? scenarioConditionProto.getBoolValue() : scenarioConditionProto.getIntValue())")
    ScenarioConditionAvro toScenarioConditionAvro(ScenarioConditionProto scenarioConditionProto);

    ScenarioRemovedEventAvro toScenarioRemovedEventAvro(ScenarioRemovedEvent scenarioRemovedEvent);

    ScenarioRemovedEventAvro toScenarioRemovedEventAvro(ScenarioRemovedEventProto scenarioRemovedEventProto);
}
