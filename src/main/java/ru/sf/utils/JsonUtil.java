package ru.sf.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.sf.deserializers.LocalDateDeserializer;
import ru.sf.models.Student;
import ru.sf.models.University;
import ru.sf.serializers.LocalDateSerializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private JsonUtil() {
    }

    public static <T> String modelToJson(T model) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson.toJson(model);
    }

    public static <T> String modelListToJson(List<T> modelList) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson.toJson(modelList);
    }
}
