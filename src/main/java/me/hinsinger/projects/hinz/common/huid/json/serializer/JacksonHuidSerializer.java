package me.hinsinger.projects.hinz.common.huid.json.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import me.hinsinger.projects.hinz.common.huid.HUID;

public class JacksonHuidSerializer extends JsonSerializer<HUID> {
    
	@Override
    public void serialize(HUID huid, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(huid.toUUID().toString());
    }
}
