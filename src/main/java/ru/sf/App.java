package ru.sf;

import ru.sf.enums.StudyProfile;
import ru.sf.models.Student;
import ru.sf.models.University;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) throws MalformedURLException {
        Student student1 = new Student.Builder()
                .withFullName("Иванов Иван Иванович")
                .withUniversityId("01")
                .withCurrentCourseNumber(1)
                .withAvgExamScore(0)
                .withDateOfBirth(LocalDateTime.of(1980, Month.JUNE, 5, 0, 0))
                .build();

        // Getter and Setter test
        if (student1 != null) {
            System.out.println(student1);
            student1.setFullName("dfgdfg");
            System.out.println(student1);
            System.out.println(student1.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }

        University university1 = new University.Builder()
                .withId("01")
                .withFullName("Московский государственный технический университет имени Н.Э.Баумана")
                .withShortName("МГТУ имени Н.Э. Баумана")
                .withYearOfFoundation(1830)
                .withMainProfile(StudyProfile.MATHEMATICS)
                .withWebsite(new URL("https://www.bmstu.ru/"))
                .build();

        // Getter and Setter test
        if (university1 !=  null) {
            System.out.println(university1);
            university1.setFullName("qweqwe");
            System.out.println(university1);
            System.out.println(university1.getFullName());
        }

    }
}
