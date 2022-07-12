package ru.sf;

import ru.sf.enums.StudentComparatorEnum;
import ru.sf.enums.StudyProfile;
import ru.sf.enums.UniversityComparatorEnum;
import ru.sf.exceptions.AppException;
import ru.sf.models.Statistics;
import ru.sf.models.Student;
import ru.sf.models.University;
import ru.sf.export.ExportStructure;
import ru.sf.utils.ComparatorSelector;
import ru.sf.utils.JsonUtil;
import ru.sf.utils.PropertiesReader;
import ru.sf.utils.StatisticBuilder;
import ru.sf.xlsxutils.XLSXParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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

    public static void main(String[] args) throws MalformedURLException {

        try {
            LogManager.getLogManager().readConfiguration(App.class.getResourceAsStream(LOG_PROPERTIES_FILENAME));
        } catch (IOException e) {
            System.out.println("Error loading logger properties\n" + e);
        }

        try {
            properties = PropertiesReader.loadProperties(APP_PROPERTIES_FILENAME);
            Path sourceFilepath = Paths.get(properties.getProperty("SOURCE_FILEPATH"));

            String xmlDestFilepathPattern = properties.getProperty("XML_DEST_FILEPATH_PATTERN");
            String jasonDestFilepathPattern = properties.getProperty("JSON_DEST_FILEPATH_PATTERN");

//            ExportStructure structure = getStructure(sourceFilepath);
//
//            Path xmlDestFilepath = getExportFilepath(xmlDestFilepathPattern, LocalDate.now());
//            new XMLExporter().exportToFile(structure, xmlDestFilepath);
//
//            Path jsonDestFilepath = getExportFilepath(jasonDestFilepathPattern, structure.getTimestamp());
//            new JSONExporter().exportToFile(structure, jsonDestFilepath);

            // Additional task - GENERICS
            // Parsing model to JSON:
            modelToJsonDemo();
            // Parsing list of models to JSON:
            modelListToJsonDemo(sourceFilepath);


        } catch (RuntimeException | AppException e) {
            logger.log(Level.SEVERE, "Application error", e);
        }
    }

    private static Path getExportFilepath(String pattern, LocalDate creationDate) {
        String datePattern = pattern.substring(pattern.indexOf('{') + 1, pattern.lastIndexOf('}'));
        String filepath = pattern.replaceFirst(datePattern, creationDate.format(DateTimeFormatter.ofPattern(datePattern)));
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

    private static void modelToJsonDemo() throws MalformedURLException {
        University university = new University.Builder()
                .withId("01")
                .withFullName("abc")
                .withShortName("ABC")
                .withYearOfFoundation(1000)
                .withMainProfile(StudyProfile.CHEMISTRY)
                .withWebsite(new URL("https://abc.ru"))
                .build();

        Student student = new Student.Builder()
                .withUniversityId("01")
                .withFullName("abc")
                .withCurrentCourseNumber(2)
                .withAvgExamScore(2.5F)
                .withDateOfBirth(LocalDate.of(1980, Month.MAY, 15))
                .build();

        System.out.println(JsonUtil.modelToJson(university));
        System.out.println(JsonUtil.modelToJson(student));
    }

    private static void modelListToJsonDemo(Path filepath) throws AppException {

        List<Student> studentList = XLSXParser.getAllStudentsFromXLSX(filepath);
        System.out.println(JsonUtil.modelListToJson(studentList));

        List<University> universityList = XLSXParser.getAllUniversitiesFromXLSX(filepath);
        System.out.println(JsonUtil.modelToJson(universityList));

    }
}
