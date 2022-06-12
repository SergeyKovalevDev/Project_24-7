package ru.sf.utils;

import ru.sf.enums.StudentComparatorEnum;
import ru.sf.enums.UniversityComparatorEnum;
import ru.sf.models.Student;
import ru.sf.models.University;

import java.util.Comparator;

public class ComparatorSelector {

    private ComparatorSelector() {}

    public static Comparator<Student> getStudentComparator(StudentComparatorEnum comparatorEnum) {
        return comparatorEnum.getComparatorType();
    }

    public static Comparator<University> getUniversityComparator(UniversityComparatorEnum comparatorEnum) {
        return comparatorEnum.getComparatorType();
    }
}
