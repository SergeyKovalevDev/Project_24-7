package ru.sf.comparators;

import org.apache.commons.lang3.StringUtils;
import ru.sf.models.Student;

public class StudentUniversityIdComparator implements StudentComparatorInterface {
    @Override
    public int compare(Student o1, Student o2) {
        return StringUtils.compare(o1.getUniversityId(), o2.getUniversityId());
    }
}
