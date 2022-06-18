package ru.sf;

import com.google.gson.JsonElement;
import ru.sf.enums.StudentComparatorEnum;
import ru.sf.enums.StudyProfile;
import ru.sf.enums.UniversityComparatorEnum;
import ru.sf.models.Student;
import ru.sf.models.University;
import ru.sf.parser.XLSXParser;
import ru.sf.utils.ComparatorSelector;
import ru.sf.utils.JsonUtil;
import ru.sf.utils.PropertiesReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App {
    public static Properties properties;

    public static void main(String[] args) {
        String sourceFilename = "src/main/resources/universityInfo.xlsx";
        String propertiesFilename = "app.properties";
        Path filepath = Paths.get(sourceFilename);
        properties = new PropertiesReader().loadProperties(propertiesFilename);
        if (properties != null) {
            XLSXParser xlsxParser = XLSXParser.getInstance();
            Comparator<Student> studentComparator = ComparatorSelector.getStudentComparator(StudentComparatorEnum.BY_FULL_NAME);
            Comparator<University> universityComparator = ComparatorSelector.getUniversityComparator(UniversityComparatorEnum.BY_YEAR_OF_FOUNDATION);

//            xlsxParser.getAllStudentsFromXLSX(filepath)
//                    .stream()
//                    .sorted(studentComparator)
//                    .forEach(System.out::println);
//
//            xlsxParser.getAllUniversitiesFromXLSX(filepath)
//                    .stream()
//                    .sorted(universityComparator)
//                    .forEach(System.out::println);

            System.out.println("Serialize Student into Json String:");
            Student student = new Student.Builder()
                    .withUniversityId("01")
                    .withFullName("abc")
                    .withCurrentCourseNumber(2)
                    .withAvgExamScore(2.5F)
                    .withDateOfBirth(LocalDate.of(1980, Month.MAY, 15))
                    .build();
            String studentJsonString = JsonUtil.studentToJson(student);
            System.out.println(studentJsonString);
            System.out.println("----------");

            System.out.println("Deserialize Json String into Student:");
            Student student1 = JsonUtil.jsonToStudent(studentJsonString);
            System.out.println(student1);
            System.out.println("----------");

            System.out.println("Serialize University into Json String:");
            University university = new University.Builder()
                    .withId("01")
                    .withFullName("abc")
                    .withShortName("ABC")
                    .withYearOfFoundation(1000)
                    .withMainProfile(StudyProfile.CHEMISTRY)
                    .build();
            String universityJsonString = JsonUtil.universityToJson(university);
            System.out.println(universityJsonString);
            System.out.println("----------");

            System.out.println("Deserialize Json String into University:");
            University university1 = JsonUtil.jsonToUniversity(universityJsonString);
            System.out.println(university1);
            System.out.println("----------");

            System.out.println("Serialize List of Student into Json String:");
            List<Student> sourceStudentList = xlsxParser.getAllStudentsFromXLSX(filepath);
            String studentListJsonString = JsonUtil.studentListToJson(sourceStudentList);
            System.out.println(studentListJsonString);
            System.out.println("----------");

            System.out.println("Deserialize Json String into List of Student");
            List<Student> deserializedStudentList = JsonUtil.jsonToStudentList(studentListJsonString);
            System.out.println(deserializedStudentList);
            System.out.println("----------");

            System.out.println("Serialize List of University into Json String:");
            List<University> sourceUniversityList = xlsxParser.getAllUniversitiesFromXLSX(filepath);
            String universityListJsonString = JsonUtil.universityListToJson(sourceUniversityList);
            System.out.println(universityListJsonString);
            System.out.println("----------");

            System.out.println("Deserialize Json String into List of University");
            List<University> deserializedUniversityList = JsonUtil.jsonToUniversityList(universityListJsonString);
            System.out.println(deserializedUniversityList);
            System.out.println("----------");

            System.out.println("Comparison of the number of elements in the original and deserialized collections of Student: " +
                    (sourceStudentList.size() == deserializedStudentList.size()));

            System.out.println("Comparison of the number of elements in the original and deserialized collections of University: " +
                    (sourceUniversityList.size() == deserializedUniversityList.size()));
            System.out.println("----------");

            System.out.println("Serialization and deserialization of Student using the Stream API:");
            sourceStudentList
                    .stream()
                    .filter(student2 -> student2.getUniversityId().equals("0004-high"))
                    .map(JsonUtil::studentToJson)
                    .peek(System.out::println)
                    .map(JsonUtil::jsonToStudent)
                    .forEach(System.out::println);
            System.out.println("----------");

            System.out.println("Serialization and deserialization of University using the Stream API:");
            sourceUniversityList
                    .stream()
                    .filter(university2 -> university2.getMainProfile().equals(StudyProfile.PHYSICS))
                    .map(JsonUtil::universityToJson)
                    .peek(System.out::println)
                    .map(JsonUtil::jsonToUniversity)
                    .forEach(System.out::println);

        }
    }
}
