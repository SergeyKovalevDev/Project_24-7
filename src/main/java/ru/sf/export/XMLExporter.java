package ru.sf.export;

import ru.sf.exceptions.AppException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class XMLExporter implements Exportable {

    @Override
    public void exportToFile(ExportStructure structure, Path filePath) throws AppException {
        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (Writer writer = new FileWriter(String.valueOf(filePath))) {
            JAXBContext context = JAXBContext.newInstance(ExportStructure.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(structure, writer);
        } catch (JAXBException e) {
            throw new AppException("Error JAXB");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
