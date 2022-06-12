package ru.sf;

import ru.sf.enums.StudentComparatorEnum;
import ru.sf.enums.UniversityComparatorEnum;
import ru.sf.models.Student;
import ru.sf.models.University;
import ru.sf.parser.XLSXParser;

import java.util.Comparator;

public class App {
    public static void main(String[] args) {
        XLSXParser xlsxParser = XLSXParser.getInstance();
        String filename = "src/main/resources/universityInfo.xlsx";

        Comparator<Student> studentComparator = ComparatorSelector.getStudentComparator(StudentComparatorEnum.BY_FULL_NAME);
        Comparator<University> universityComparator = ComparatorSelector.getUniversityComparator(UniversityComparatorEnum.BY_YEAR_OF_FOUNDATION);

        xlsxParser.getAllStudentsFromXLSX(filename)
                .stream()
                .sorted(studentComparator)
                .forEach(System.out::println);

        xlsxParser.getAllUniversitiesFromXLSX(filename)
                .stream()
                .sorted(universityComparator)
                .forEach(System.out::println);
    }
}
