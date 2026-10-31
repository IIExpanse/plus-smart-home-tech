package ru.yandex.practicum.telemetry.collector.controller.event.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.yandex.practicum.grpc.telemetry.controller.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.telemetry.collector.service.event.EventService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class GrpcEventController extends CollectorControllerGrpc.CollectorControllerImplBase {
    private final EventService eventService;

    @Override
    public void collectSensorEvent(SensorEventProto request, StreamObserver<Empty> responseObserver) {
        if (log.isDebugEnabled()) {
            log.debug("Received gRPC sensor event: {}", request);
        }
        eventService.sendSensorEvent(request);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
        if (log.isDebugEnabled()) {
            log.debug("Successfully processed gRPC sensor event: {}", request);
        }
    }

    @Override
    public void collectHubEvent(HubEventProto request, StreamObserver<Empty> responseObserver) {
        if (log.isDebugEnabled()) {
            log.debug("Received gRPC hub event: {}", request);
        }
        eventService.sendHubEvent(request);
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
        if (log.isDebugEnabled()) {
            log.debug("Successfully processed gRPC hub event: {}", request);
        }
    }
}
