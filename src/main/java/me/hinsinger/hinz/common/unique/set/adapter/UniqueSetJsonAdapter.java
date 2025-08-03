package me.hinsinger.hinz.common.unique.set.adapter;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import me.hinsinger.hinz.common.json.adapter.JsonAdapter;
import me.hinsinger.hinz.common.unique.set.UniqueSet;


// TODO write tests
public class UniqueSetJsonAdapter<T> implements JsonAdapter<UniqueSet<T>> {

	private final Class<T> elementType;

	public UniqueSetJsonAdapter(Class<T> elementType) {
		this.elementType = elementType;
	}

	@Override
	public JsonElement serialize(UniqueSet<T> src, Type typeOfSrc, JsonSerializationContext context) {
		JsonArray array = new JsonArray();
		
		for (T element : src) {
			array.add(context.serialize(element, elementType));
		}
		
		return array;
	}

	@Override
	public UniqueSet<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		if (!json.isJsonArray()) {
			throw new JsonParseException("Expected JSON array for UniqueSet");
		}

		JsonArray array = json.getAsJsonArray();
		UniqueSet<T> set = new UniqueSet<>();
		
		for (JsonElement element : array) {
			T obj = context.deserialize(element, elementType);
			set.add(obj);
		}
		
		return set;
	}
}
