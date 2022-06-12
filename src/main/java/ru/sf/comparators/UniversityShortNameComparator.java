package ru.sf.comparators;

import org.apache.commons.lang3.StringUtils;
import ru.sf.models.University;

public class UniversityShortNameComparator implements UniversityComparatorInterface {
    @Override
    public int compare(University o1, University o2) {
        return StringUtils.compare(o1.getShortName(), o2.getShortName());
    }
}
