package me.hinsinger.hinz.common.huid.json.deserializer;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import me.hinsinger.hinz.common.huid.HUID;

public class GsonHuidDeserializer implements JsonDeserializer<HUID> {

	@Override
	public HUID deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		return HUID.fromString(json.getAsString());
	}


}
