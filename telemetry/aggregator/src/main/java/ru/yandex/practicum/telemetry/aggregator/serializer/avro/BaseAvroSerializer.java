package ru.yandex.practicum.telemetry.aggregator.serializer.avro;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class BaseAvroSerializer<T extends SpecificRecord> implements Serializer<T> {
    private final DatumWriter<SpecificRecord> datumWriter;
    private BinaryEncoder encoder;

    public BaseAvroSerializer(DatumWriter<SpecificRecord> datumWriter) {
        this.datumWriter = datumWriter;
    }

    @Override
    public byte[] serialize(String topic, T data) {
        if (log.isDebugEnabled()) {
            log.debug("Serializing record for topic {}.", topic);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.encoder = EncoderFactory.get().binaryEncoder(outputStream, this.encoder);

        try {
            datumWriter.write(data, encoder);
            encoder.flush();
        } catch (IOException e) {
            log.error("Serializing record for topic {}: {}", topic, e.getMessage());
            throw new RuntimeException(e);
        }
        if (log.isDebugEnabled()) {
            log.debug("Record serialized for topic {}.", topic);
        }

        return outputStream.toByteArray();
    }
}
