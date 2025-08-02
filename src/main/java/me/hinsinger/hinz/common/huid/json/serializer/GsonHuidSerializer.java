package me.hinsinger.hinz.common.huid.json.serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import me.hinsinger.hinz.common.huid.HUID;

public class GsonHuidSerializer implements JsonSerializer<HUID> {

	@Override
	public JsonElement serialize(HUID huid, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(huid.toUUID().toString());
	}

}
