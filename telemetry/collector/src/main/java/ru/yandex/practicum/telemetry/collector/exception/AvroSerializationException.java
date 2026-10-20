package ru.yandex.practicum.telemetry.collector.exception;

public class AvroSerializationException extends RuntimeException {
    public AvroSerializationException(String message) {
        super(message);
    }

    public AvroSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
