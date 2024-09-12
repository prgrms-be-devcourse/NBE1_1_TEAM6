package programmers.coffee.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtil() {
    }

    public static String objectToString(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        return "{}";
    }

    public static <T> T stringToObject(String string, Class<T> tClass) {
        try {
            MAPPER.readValue(string, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static <K, V> Map<K, V> stringToMap(String string, Class<K> keyType, Class<V> valueType) {
        try {
            return MAPPER.readValue(string,
                    MapType.construct(HashMap.class, null, null, null, SimpleType.constructUnsafe(keyType),
                            SimpleType.constructUnsafe(valueType)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        return null;
    }

}
