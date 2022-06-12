package ru.sf.comparators;

import ru.sf.models.University;

public class UniversityYearOfFoundationComparator implements UniversityComparatorInterface {
    @Override
    public int compare(University o1, University o2) {
        return Integer.compare(o1.getYearOfFoundation(), o2.getYearOfFoundation());
    }
}
