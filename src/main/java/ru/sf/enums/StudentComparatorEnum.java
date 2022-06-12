package ru.sf.enums;

import ru.sf.comparators.*;
import ru.sf.models.Student;

import java.util.Comparator;

public enum StudentComparatorEnum {
    BY_AVG_EXAM_SCORE (new StudentAvgExamScoreComparator()),
    BY_CURRENT_COURSE_NUMBER (new StudentCurrentCourseNumberComparator()),
    BY_DATE_OF_BIRTH (new StudentDateOfBirthComparator()),
    BY_FULL_NAME (new StudentFullNameComparator()),
    BY_UNIVERSITY_ID (new StudentUniversityIdComparator());

    private final Comparator<Student> comparatorType;

    StudentComparatorEnum(Comparator<Student> comparatorType) {
        this.comparatorType = comparatorType;
    }

    public Comparator<Student> getComparatorType() {
        return comparatorType;
    }
}
