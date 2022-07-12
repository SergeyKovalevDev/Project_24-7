package ru.sf.export;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.sf.exceptions.AppException;
import ru.sf.serializers.LocalDateSerializer;
import ru.sf.xlsxutils.XLSXParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.logging.Logger;

public class JSONExporter implements Exportable {

    public static final Logger logger = Logger.getLogger(XLSXParser.class.getName());

    @Override
    public void exportToFile(ExportStructure structure, Path filePath) throws AppException {
        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException ex) {
            throw new AppException("Error saving the structure to a file \"" + filePath + "\"");
        }
        try (Writer writer = new FileWriter(String.valueOf(filePath))) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            gson.toJson(structure, writer);
            logger.info("The structure has been successfully saved to a file \"" + filePath + "\"");
        } catch (IOException e) {
            throw new AppException("Error saving the structure to a file \"" + filePath + "\"");
        }
    }
}
