package me.hinsinger.projects.hinz.common.huid;

import java.util.UUID;

import org.springframework.lang.NonNull;

import com.eatthepath.uuid.FastUUID;

import me.hinsinger.projects.hinz.common.huid.json.deserializer.GsonHuidDeserializer;
import me.hinsinger.projects.hinz.common.huid.json.deserializer.JacksonHuidDeserializer;
import me.hinsinger.projects.hinz.common.huid.json.serializer.GsonHuidSerializer;
import me.hinsinger.projects.hinz.common.huid.json.serializer.JacksonHuidSerializer;
import me.hinsinger.projects.hinz.common.json.JsonUtil;
import me.hinsinger.projects.hinz.common.random.RandomUtil;

public class HUID {
	private static final String UUID_REGEX = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}";
    private static final String TRIMMED_UUID_REGEX = "[a-f0-9]{12}4[a-f0-9]{3}[89aAbB][a-f0-9]{15}";
    private static final String ADD_UUID_HYPHENS_REGEX = "([a-f0-9]{8})([a-f0-9]{4})(4[a-f0-9]{3})([89aAbB][a-f0-9]{3})([a-f0-9]{12})";
	
    private UUID uuid;
    
    static {
    	JsonUtil.registerAdapter(HUID.class, new JacksonHuidSerializer(), new JacksonHuidDeserializer());
    	JsonUtil.registerAdapter(HUID.class, new GsonHuidSerializer(), new GsonHuidDeserializer());
    }
    
    public static HUID random() {
    	return new HUID(RandomUtil.uuid());
    }
    
    public static HUID fromString(@NonNull String muid) {
    	if(!isHUID(muid)) throw new IllegalArgumentException("Provided muid %s is not an muid identifier!".formatted(muid));
    	
    	if(muid.contains("-")) return new HUID(FastUUID.parseUUID(muid));

    	return new HUID(FastUUID.parseUUID(muid.replaceAll(ADD_UUID_HYPHENS_REGEX, "$1-$2-$3-$4-$5")));
    }
    
    public static HUID fromUUID(@NonNull UUID uuid) {
    	return new HUID(uuid);
    }
    
    public static boolean isHUID(String muid) {
    	return (muid != null) && (muid.matches(TRIMMED_UUID_REGEX) || muid.matches(UUID_REGEX));
    }
	
	private HUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	public String trim() {
		return FastUUID.toString(uuid).replace("-", "");
	}
	
	public UUID toUUID() {
		return uuid;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object == this) return true;
		
		else if(object instanceof UUID) return uuid.equals((UUID) object);
		else if(object instanceof HUID) return uuid.equals(((HUID) object).uuid);
		else if(object instanceof String && isHUID((String) object)) {
			return equals(fromString((String) object));
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return uuid.hashCode();
	}
	
	@Override
	public String toString() {
		return trim();
	}
}
