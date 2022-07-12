package ru.sf.export;

import ru.sf.exceptions.AppException;
import ru.sf.xlsxutils.XLSXParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class XMLExporter implements Exportable {

    public static final Logger logger = Logger.getLogger(XLSXParser.class.getName());

    @Override
    public void exportToFile(ExportStructure structure, Path filePath) throws AppException {
        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException e) {
            throw new AppException("Error saving the structure to a file \"" + filePath + "\"");
        }
        try (Writer writer = new FileWriter(String.valueOf(filePath))) {
            JAXBContext context = JAXBContext.newInstance(ExportStructure.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(structure, writer);
            logger.info("The structure has been successfully saved to a file \"" + filePath + "\"");
        } catch (JAXBException e) {
            throw new AppException("Error converting the structure to xml\n" + e);
        } catch (IOException e) {
            throw new AppException("Error saving the structure to a file \"" + filePath + "\"");
        }
    }
}
