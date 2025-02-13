package me.hinsinger.projects.hinz.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
	private static Gson gson = new Gson();
	private static ObjectMapper jackson = new ObjectMapper(); //TODO remove Jackson and use GSON typetoken
	
	public static String toJson(Object object) {
		return gson.toJson(object);
	}
	
	public static <T> T fromJson(Class<T> clazz, String json) {
		return gson.fromJson(json, clazz);
	}
	
	public static <T> T fromJson(Class<T> clazz, JsonElement json) {
		return gson.fromJson(json, clazz);
	}
	
	public static <T> T fromJson(TypeToken<T> token, String json) {
		return gson.fromJson(json, token);
	}
	
	public static <T> T fromJson(TypeToken<T> token, JsonElement json) {
		return gson.fromJson(json, token);
	}
	
	public static <T> void registerAdapter(Class<T> clazz, com.google.gson.JsonSerializer<T> serializer, com.google.gson.JsonDeserializer<T> deserializer) {
		gson = gson.newBuilder()
			.registerTypeAdapter(clazz, serializer)
			.registerTypeAdapter(clazz, deserializer)
			.create();
	}
	
	public static <T> void registerAdapter(Class<T> clazz, com.fasterxml.jackson.databind.JsonSerializer<T> serializer, com.fasterxml.jackson.databind.JsonDeserializer<T> deserializer) {
		SimpleModule module = new SimpleModule();
        module.addSerializer(clazz, serializer);
        module.addDeserializer(clazz, deserializer);
		
		jackson.registerModule(module);
	}
}
