package me.hinsinger.projects.hinz.common.huid.json.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import me.hinsinger.projects.hinz.common.huid.HUID;

public class JacksonHuidDeserializer extends JsonDeserializer<HUID> {

	@Override
	public HUID deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		return HUID.fromString(p.getValueAsString());
	}

}
