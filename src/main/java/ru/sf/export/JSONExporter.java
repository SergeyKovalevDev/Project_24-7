package ru.sf.export;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.sf.exceptions.AppException;
import ru.sf.serializers.LocalDateSerializer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class JSONExporter implements Exportable {
    @Override
    public void exportToFile(ExportStructure structure, Path filePath) throws AppException {
        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try (Writer writer = new FileWriter(String.valueOf(filePath))) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            gson.toJson(structure, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
