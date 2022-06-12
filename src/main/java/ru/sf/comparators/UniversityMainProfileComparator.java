package ru.sf.comparators;

import org.apache.commons.lang3.StringUtils;
import ru.sf.models.University;

public class UniversityMainProfileComparator implements UniversityComparatorInterface {
    @Override
    public int compare(University o1, University o2) {
        return StringUtils.compare(o1.getMainProfile().toString(), o2.getMainProfile().toString());
    }
}
