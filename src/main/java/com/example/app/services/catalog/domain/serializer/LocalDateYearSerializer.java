package com.example.app.services.catalog.domain.serializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.IOException;
import java.io.Serial;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateYearSerializer extends LocalDateSerializer {
    @Serial
    private static final long serialVersionUID = 1L;

    public LocalDateYearSerializer() {
        super();
    }

    public LocalDateYearSerializer(LocalDateSerializer base,
                                   Boolean useTimestamp, DateTimeFormatter dtf, JsonFormat.Shape shape) {
        super(base, useTimestamp, dtf, shape);
    }

    public LocalDateYearSerializer(DateTimeFormatter formatter) {
        super(formatter);
    }

    @Override
    protected LocalDateYearSerializer withFormat(Boolean useTimestamp, DateTimeFormatter dtf, JsonFormat.Shape shape) {
        return new LocalDateYearSerializer(this, useTimestamp, dtf, shape);
    }

    @Override
    public void serialize(LocalDate date, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (useTimestamp(provider)) {
            if (_shape == JsonFormat.Shape.NUMBER_INT) {
                gen.writeNumber(date.toEpochDay());
            } else {
                gen.writeStartArray();
                _serializeAsArrayContents(date, gen, provider);
                gen.writeEndArray();
            }
        } else {
            gen.writeString((_formatter == null) ? String.valueOf(date.getYear()) : date.format(_formatter));
        }
    }

    @Override
    public void serializeWithType(LocalDate value, JsonGenerator gen,
                                  SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        var typeIdDef = typeSer.writeTypePrefix(gen, typeSer.typeId(value, serializationShape(provider)));
        switch (typeIdDef.valueShape) {
            case START_ARRAY -> _serializeAsArrayContents(value, gen, provider);
            case VALUE_NUMBER_INT -> gen.writeNumber(value.toEpochDay());
            default -> gen.writeString((_formatter == null)
                    ? String.valueOf(value.getYear()) : value.format(_formatter));
        }
        typeSer.writeTypeSuffix(gen, typeIdDef);
    }

    protected void _serializeAsArrayContents(LocalDate value, JsonGenerator gen,
                                             SerializerProvider provider) throws IOException {
        gen.writeNumber(value.getYear());
    }
}
