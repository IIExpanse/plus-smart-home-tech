package ru.yandex.practicum.telemetry.aggregator.deserializer.avro;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class BaseAvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {
    private final DecoderFactory decoderFactory;
    private BinaryDecoder decoder;
    private SpecificDatumReader<T> reader;

    public BaseAvroDeserializer(Schema schema) {
        this(DecoderFactory.get(), schema);
    }

    public BaseAvroDeserializer(DecoderFactory decoderFactory, Schema schema) {
        this.decoderFactory = decoderFactory;
        this.reader = new SpecificDatumReader<>(schema);
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (log.isDebugEnabled()) {
            log.debug("Deserializing data from topic {}", topic);
        }
        this.decoder = decoderFactory.binaryDecoder(data, this.decoder);
        if (data == null) {
            return null;
        }
        try {
            T result = reader.read(null, decoder);
            if (log.isDebugEnabled()) {
                log.debug("Deserialized data from topic {}", topic);
            }
            return result;
        } catch (IOException e) {
            log.error("Error while deserializing data from topic {}: {}", topic, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
