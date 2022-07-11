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

    public static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {

        try {
            LogManager.getLogManager().readConfiguration(App.class.getResourceAsStream("/logging.properties"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String sourceFilename = "src/main/resources/universityInfo.xlsx";
        Path source = Paths.get(sourceFilename);
        String destinationFilename = "statistic.xlsx";
        String propertiesFilename = "app.properties";
        properties = new PropertiesReader().loadProperties(propertiesFilename);
        if (properties != null) {
            XLSXParser xlsxParser = XLSXParser.getInstance();
//            parsingAndSortingUsingStreamApi(source, xlsxParser);
            try {
                getStatisticalReport(source, destinationFilename, xlsxParser);
            } catch (AppException e) {
                logger.log(Level.SEVERE,"Application error", e);
            }
        }
    }

    private static void parsingAndSortingUsingStreamApi(Path filepath, XLSXParser xlsxParser) throws AppException {
        Comparator<Student> studentComparator = ComparatorSelector.getStudentComparator(StudentComparatorEnum.BY_FULL_NAME);
        Comparator<University> universityComparator = ComparatorSelector.getUniversityComparator(UniversityComparatorEnum.BY_YEAR_OF_FOUNDATION);

        List<Student> studentList = xlsxParser.getAllStudentsFromXLSX(filepath)
                .stream()
                .sorted(studentComparator)
                .toList();

        List<University> universityList = xlsxParser.getAllUniversitiesFromXLSX(filepath)
                .stream()
                .sorted(universityComparator)
                .toList();
    }

    private static void getStatisticalReport(Path sourcePath, String destFilename, XLSXParser xlsxParser) throws AppException {
        List<University> universityList = xlsxParser.getAllUniversitiesFromXLSX(sourcePath);
        List<Student> studentList = xlsxParser.getAllStudentsFromXLSX(sourcePath);
        List<Statistics> statisticsList = StatisticBuilder.getStatistic(studentList, universityList);
        XLSXWriter.createWorkbook(statisticsList, destFilename);
    }
}
