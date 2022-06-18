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

    public static String studentToJson(Student student) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson.toJson(student);
    }

    public static String universityToJson(University university) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson.toJson(university);
    }

    public static String studentListToJson(List<Student> studentList) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        return gson.toJson(studentList);
    }

    public static String universityListToJson(List<University> universityList) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson.toJson(universityList);
    }

    public static Student jsonToStudent(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonString, Student.class);
    }

    public static University jsonToUniversity(String jsonString) {
        return new Gson().fromJson(jsonString, University.class);
    }

    public static List<Student> jsonToStudentList(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonString, new TypeToken<ArrayList<Student>>() {}.getType());
    }

    public static List<University> jsonToUniversityList(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, new TypeToken<ArrayList<University>>() {}.getType());
    }
}
