package ru.sf;

import ru.sf.enums.StudentComparatorEnum;
import ru.sf.enums.UniversityComparatorEnum;
import ru.sf.exceptions.AppException;
import ru.sf.models.Statistics;
import ru.sf.models.Student;
import ru.sf.models.University;
import ru.sf.utils.ComparatorSelector;
import ru.sf.utils.PropertiesReader;
import ru.sf.utils.StatisticBuilder;
import ru.sf.xlsxutils.XLSXParser;
import ru.sf.xlsxutils.XLSXWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
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
            Path sourcePath = Paths.get(properties.getProperty("SOURCE_FILENAME"));
            String destinationFilename = properties.getProperty("DESTINATION_FILENAME");
            getStatisticalReport(sourcePath, destinationFilename);
        } catch (AppException e) {
            logger.log(Level.SEVERE, "Application error", e);
        }
    }

    private static void parsingAndSortingUsingStreamApi(Path filepath) throws AppException {
        Comparator<Student> studentComparator = ComparatorSelector.getStudentComparator(StudentComparatorEnum.BY_FULL_NAME);
        Comparator<University> universityComparator = ComparatorSelector.getUniversityComparator(UniversityComparatorEnum.BY_YEAR_OF_FOUNDATION);

        List<Student> studentList = XLSXParser.getAllStudentsFromXLSX(filepath)
                .stream()
                .sorted(studentComparator)
                .toList();

        List<University> universityList = XLSXParser.getAllUniversitiesFromXLSX(filepath)
                .stream()
                .sorted(universityComparator)
                .toList();
    }

    private static void getStatisticalReport(Path sourcePath, String destFilename) throws AppException {
        List<University> universityList = XLSXParser.getAllUniversitiesFromXLSX(sourcePath);
        List<Student> studentList = XLSXParser.getAllStudentsFromXLSX(sourcePath);
        List<Statistics> statisticsList = StatisticBuilder.getStatistic(studentList, universityList);
        XLSXWriter.createWorkbook(statisticsList, destFilename);
    }
}
