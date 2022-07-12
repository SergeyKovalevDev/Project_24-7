package ru.sf.exportutils;

import ru.sf.exceptions.AppException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class XMLExporter implements Exportable {

    @Override
    public void exportToFile(ExportStructure structure, Path filePath) throws AppException {
        try {
            Files.createDirectories(filePath.getParent());
            JAXBContext context = JAXBContext.newInstance(ExportStructure.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(structure, Files.newOutputStream(filePath, StandardOpenOption.CREATE));
        } catch (JAXBException e) {
            throw new AppException("Error JAXB");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
