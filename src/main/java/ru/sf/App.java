package ru.sf;

import ru.sf.enums.StudentComparatorEnum;
import ru.sf.enums.UniversityComparatorEnum;
import ru.sf.exceptions.AppException;
import ru.sf.exportutils.XMLExporter;
import ru.sf.models.Statistics;
import ru.sf.models.Student;
import ru.sf.models.University;
import ru.sf.exportutils.Exportable;
import ru.sf.exportutils.ExportStructure;
import ru.sf.utils.ComparatorSelector;
import ru.sf.utils.PropertiesReader;
import ru.sf.utils.StatisticBuilder;
import ru.sf.xlsxutils.XLSXParser;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class App {
    public static Properties properties;
    private static final String APP_PROPERTIES_FILENAME = "/app.properties";
    private static final String LOG_PROPERTIES_FILENAME = "/logging.properties";
    public static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {

        try {
            LogManager.getLogManager().readConfiguration(App.class.getResourceAsStream(LOG_PROPERTIES_FILENAME));
        } catch (IOException e) {
            System.out.println("Error loading logger properties\n" + e);
        }

        try {
            properties = PropertiesReader.loadProperties(APP_PROPERTIES_FILENAME);
            Path sourceFilepath = Paths.get(properties.getProperty("SOURCE_FILEPATH"));

            String xmlDestFilepathPattern = properties.getProperty("XML_DEST_FILEPATH_PATTERN");

            Path xmlDestFilepath = getExportFilepath(xmlDestFilepathPattern);
            ExportStructure structure = getStructure(sourceFilepath);
            xmlExport(structure, xmlDestFilepath);
        } catch (RuntimeException | AppException e) {
            logger.log(Level.SEVERE, "Application error", e);
        }
    }

    private static void xmlExport(ExportStructure structure, Path destFilepath) throws AppException {
        Exportable xmlExporter = new XMLExporter();
        xmlExporter.exportToFile(structure, destFilepath);
    }

    private static Path getExportFilepath(String pattern) {
        String datePattern = pattern.substring(pattern.indexOf('{') + 1, pattern.lastIndexOf('}'));
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        String date = dateFormat.format(new Date());
        String filepath = pattern.replaceFirst(datePattern, date);
        return Paths.get(filepath);
    }

    private static ExportStructure getStructure(Path sourceFilepath) throws AppException {

        Comparator<Student> studentComparator = ComparatorSelector.getStudentComparator(StudentComparatorEnum.BY_FULL_NAME);
        Comparator<University> universityComparator = ComparatorSelector.getUniversityComparator(UniversityComparatorEnum.BY_YEAR_OF_FOUNDATION);

        List<Student> studentList = XLSXParser.getAllStudentsFromXLSX(sourceFilepath)
                .stream()
                .sorted(studentComparator)
                .toList();

        List<University> universityList = XLSXParser.getAllUniversitiesFromXLSX(sourceFilepath)
                .stream()
                .sorted(universityComparator)
                .toList();

        List<Statistics> statisticsList = StatisticBuilder.getStatistic(studentList, universityList);

        return new ExportStructure(studentList, universityList, statisticsList);
    }
}
