package ru.sf.comparators;

import ru.sf.models.Student;

import java.time.LocalDate;

public class StudentDateOfBirthComparator implements StudentComparatorInterface {
    @Override
    public int compare(Student o1, Student o2) {
        LocalDate d1 = o1.getDateOfBirth();
        LocalDate d2 = o2.getDateOfBirth();
        if (d1 == null && d2 == null) return 0;
        else if (d1 == null) return -1;
        else if (d2 == null) return 1;
        else return d1.compareTo(d2);
    }
}
