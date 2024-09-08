package uz.pdp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import uz.pdp.model.Duolar;

import java.io.File;
import java.util.List;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void writeGson(String path, T t) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            objectMapper.writeValue(new File(path), t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static <T> List<T> readGson(String path, TypeReference<List<T>> typeReference) {
        try {
            return objectMapper.readValue(new File(path), typeReference);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
