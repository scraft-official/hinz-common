package me.hinsinger.projects.hinz.time.timestamp.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import me.hinsinger.projects.hinz.time.timestamp.Timestamp;

public class JacksonTimestampDeserializer extends JsonDeserializer<Timestamp> {

	@Override
	public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		return Timestamp.of(p.getValueAsLong());
	}

}
