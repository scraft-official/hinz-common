package me.hinsinger.projects.hinz.common.time.timestamp.json.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;

public class JacksonTimestampSerializer extends JsonSerializer<Timestamp> {
	
	@Override
    public void serialize(Timestamp timestamp, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(timestamp.toUnixMillis());
    }
}
