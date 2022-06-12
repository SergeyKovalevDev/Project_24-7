package ru.sf.enums;

import ru.sf.comparators.*;
import ru.sf.models.University;

import java.util.Comparator;

public enum UniversityComparatorEnum {
    BY_FULL_NAME (new UniversityFullNameComparator()),
    BY_ID (new UniversityIdComparator()),
    BY_MAIN_PROFILE (new UniversityMainProfileComparator()),
    BY_SHORT_NAME (new UniversityShortNameComparator()),
    BY_WEBSITE (new UniversityWebsiteComparator()),
    BY_YEAR_OF_FOUNDATION (new UniversityYearOfFoundationComparator());

    private final Comparator<University> comparatorType;

    UniversityComparatorEnum(Comparator<University> comparatorType) {
        this.comparatorType = comparatorType;
    }

    public Comparator<University> getComparatorType() {
        return comparatorType;
    }
}
