package me.hinsinger.hinz.common.json.adapter;

public interface JsonAdapter<T> extends com.google.gson.JsonSerializer<T>,
										com.google.gson.JsonDeserializer<T> {
	
	
}
