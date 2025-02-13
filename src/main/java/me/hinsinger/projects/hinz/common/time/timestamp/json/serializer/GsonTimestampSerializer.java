package me.hinsinger.projects.hinz.common.time.timestamp.json.serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;

public class GsonTimestampSerializer implements JsonSerializer<Timestamp> {

	@Override
	public JsonElement serialize(Timestamp timestamp, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(timestamp.toUnixMillis());
	}

}
