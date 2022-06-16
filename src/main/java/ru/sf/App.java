package ru.sf;

import ru.sf.enums.StudentComparatorEnum;
import ru.sf.enums.UniversityComparatorEnum;
import ru.sf.models.Student;
import ru.sf.models.University;
import ru.sf.parser.XLSXParser;
import ru.sf.utils.ComparatorSelector;
import ru.sf.utils.PropertiesReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Properties;

public class App {
    public static Properties properties;

    public static void main(String[] args) {
        String sourceFilename = "src/main/resources/universityInfo.xlsx";
        String propertiesFilename = "app.properties";
        Path filepath = Paths.get(sourceFilename);
        properties = new PropertiesReader().loadProperties(propertiesFilename);
        if (properties != null) {
            XLSXParser xlsxParser = XLSXParser.getInstance();
            Comparator<Student> studentComparator = ComparatorSelector.getStudentComparator(StudentComparatorEnum.BY_FULL_NAME);
            Comparator<University> universityComparator = ComparatorSelector.getUniversityComparator(UniversityComparatorEnum.BY_YEAR_OF_FOUNDATION);

            xlsxParser.getAllStudentsFromXLSX(filepath)
                    .stream()
                    .sorted(studentComparator)
                    .forEach(System.out::println);

            xlsxParser.getAllUniversitiesFromXLSX(filepath)
                    .stream()
                    .sorted(universityComparator)
                    .forEach(System.out::println);
        }
    }
}
