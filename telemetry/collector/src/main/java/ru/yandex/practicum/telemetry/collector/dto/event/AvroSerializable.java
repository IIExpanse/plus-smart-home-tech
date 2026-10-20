package ru.yandex.practicum.telemetry.collector.dto.event;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface AvroSerializable<T extends SpecificRecord, M> {
    static byte[] toAvroBytes(SpecificRecord avroRecord) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DatumWriter<SpecificRecord> datumWriter = new SpecificDatumWriter<>(avroRecord.getSchema());
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);

        datumWriter.write(avroRecord, encoder);
        encoder.flush();
        return outputStream.toByteArray();
    }

    static byte[] toAvroBytes(SpecificRecord avroRecord, Logger log) throws IOException {
        log.debug("Mapped avro record: {}", avroRecord);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DatumWriter<SpecificRecord> datumWriter = new SpecificDatumWriter<>(avroRecord.getSchema());
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);

        datumWriter.write(avroRecord, encoder);
        encoder.flush();
        return outputStream.toByteArray();
    }

    T toAvro(M mapper);

    default byte[] toAvroBytes(M mapper) throws IOException {
        T avroRecord = this.toAvro(mapper);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DatumWriter<T> datumWriter = new SpecificDatumWriter<>(avroRecord.getSchema());
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);

        datumWriter.write(avroRecord, encoder);
        encoder.flush();
        return outputStream.toByteArray();
    }

    default byte[] toAvroBytes(M mapper, Logger log) throws IOException {
        T avroRecord = this.toAvro(mapper);
        log.debug("Mapped avro record: {}", avroRecord);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DatumWriter<T> datumWriter = new SpecificDatumWriter<>(avroRecord.getSchema());
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);

        datumWriter.write(avroRecord, encoder);
        encoder.flush();
        return outputStream.toByteArray();
    }
}
