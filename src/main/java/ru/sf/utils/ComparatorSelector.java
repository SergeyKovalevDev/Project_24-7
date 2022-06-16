package ru.sf.utils;

import ru.sf.comparators.*;
import ru.sf.enums.StudentComparatorEnum;
import ru.sf.enums.UniversityComparatorEnum;

public class ComparatorSelector {

    private ComparatorSelector() {}

    public static StudentComparatorInterface getStudentComparator(StudentComparatorEnum comparator) {

        return switch (comparator) {

            case BY_AVG_EXAM_SCORE -> new StudentAvgExamScoreComparator();
            case BY_CURRENT_COURSE_NUMBER -> new StudentCurrentCourseNumberComparator();
            case BY_DATE_OF_BIRTH -> new StudentDateOfBirthComparator();
            case BY_FULL_NAME -> new StudentFullNameComparator();
            case BY_UNIVERSITY_ID -> new StudentUniversityIdComparator();
        };
    }

    public static UniversityComparatorInterface getUniversityComparator(UniversityComparatorEnum comparator) {
        return switch (comparator) {
            case BY_FULL_NAME -> new UniversityFullNameComparator();
            case BY_ID -> new UniversityIdComparator();
            case BY_MAIN_PROFILE -> new UniversityMainProfileComparator();
            case BY_SHORT_NAME -> new UniversityShortNameComparator();
            case BY_WEBSITE -> new UniversityWebsiteComparator();
            case BY_YEAR_OF_FOUNDATION -> new UniversityYearOfFoundationComparator();
        };
    }
}
