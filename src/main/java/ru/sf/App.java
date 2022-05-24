package ru.sf;

import ru.sf.enums.StudyProfile;
import ru.sf.models.Student;
import ru.sf.models.University;

public class App {
    public static void main(String[] args) {
        Student student1 = new Student()
                .setFullName("Иванов Иван Иванович")
                .setUniversityId("1")
                .setCurrentCourseNumber(2)
                .setAvgExamScore(4.5f);
        University university1 = new University()
                .setId("01")
                .setFullName("Московский государственный технический университет имени Н.Э.Баумана")
                .setShortName("МГТУ им. Н. Э. Баумана")
                .setYearOfFoundation(1830)
                .setMainProfile(StudyProfile.MATHEMATICS);
        System.out.println(student1);
        System.out.println(university1);
    }
}
