package ru.sf;

import ru.sf.enums.StudentComparatorEnum;
import ru.sf.enums.StudyProfile;
import ru.sf.enums.UniversityComparatorEnum;
import ru.sf.models.Student;
import ru.sf.models.University;
import ru.sf.xlsxutils.XLSXParser;
import ru.sf.utils.ComparatorSelector;
import ru.sf.utils.JsonUtil;
import ru.sf.utils.PropertiesReader;
import ru.sf.utils.StatisticBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

public class App {
    public static Properties properties;
    private static List<Student> sourceStudentList;
    private static List<Student> deserializedStudentList;
    private static List<University> sourceUniversityList;
    private static List<University> deserializedUniversityList;



    public static void main(String[] args) {
        String sourceFilename = "src/main/resources/universityInfo.xlsx";
        String propertiesFilename = "app.properties";
        Path filepath = Paths.get(sourceFilename);
        properties = new PropertiesReader().loadProperties(propertiesFilename);
        if (properties != null) {
            XLSXParser xlsxParser = XLSXParser.getInstance();

//            parsingAndSortingUsingStreamApi(filepath, xlsxParser);
/*
            studentSerializeAndDeserialize();
            universitySerializeAndDeserialize();
            studentListSerializeAndDeserialize(filepath, xlsxParser);
            universityListSerializeAndDeserialize(filepath, xlsxParser);
            numberOfElementsComparison();
            serializationAndDeserializationUsingStreamApi();
*/
            stat(filepath, xlsxParser);
        }
    }

    private static void stat(Path filepath, XLSXParser xlsxParser) {
        sourceUniversityList = xlsxParser.getAllUniversitiesFromXLSX(filepath);
        sourceStudentList = xlsxParser.getAllStudentsFromXLSX(filepath);
        StatisticBuilder.getStatistic(sourceStudentList, sourceUniversityList).forEach(System.out::println);
    }

    private static void parsingAndSortingUsingStreamApi(Path filepath, XLSXParser xlsxParser) {
        Comparator<Student> studentComparator = ComparatorSelector.getStudentComparator(StudentComparatorEnum.BY_FULL_NAME);
        Comparator<University> universityComparator = ComparatorSelector.getUniversityComparator(UniversityComparatorEnum.BY_YEAR_OF_FOUNDATION);

        xlsxParser.getAllStudentsFromXLSX(filepath)
                .stream()
                .sorted(studentComparator)
                .forEach(System.out::println);

        xlsxParser.getAllUniversitiesFromXLSX(filepath)
                .stream()
                .sorted(universityComparator)
                .forEach(System.out::println);
    }

    private static void serializationAndDeserializationUsingStreamApi() {
        System.out.println("Serialization and deserialization of Student using the Stream API:");
        sourceStudentList
                .stream()
                .filter(student -> student.getUniversityId().equals("0004-high"))
                .map(JsonUtil::studentToJson)
                .peek(System.out::println)
                .map(JsonUtil::jsonToStudent)
                .forEach(System.out::println);
        System.out.println("----------");

        System.out.println("Serialization and deserialization of University using the Stream API:");
        sourceUniversityList
                .stream()
                .filter(university -> university.getMainProfile().equals(StudyProfile.PHYSICS))
                .map(JsonUtil::universityToJson)
                .peek(System.out::println)
                .map(JsonUtil::jsonToUniversity)
                .forEach(System.out::println);
        System.out.println("----------");
    }

    private static void numberOfElementsComparison() {
        System.out.println("Comparison of the number of elements in the original and deserialized collections of Student: " +
                (sourceStudentList.size() == deserializedStudentList.size()));
        System.out.println("Comparison of the number of elements in the original and deserialized collections of University: " +
                (sourceUniversityList.size() == deserializedUniversityList.size()));
        System.out.println("----------");
    }

    private static void universityListSerializeAndDeserialize(Path filepath, XLSXParser xlsxParser) {
        System.out.println("Serialize List of University into Json String:");
        sourceUniversityList = xlsxParser.getAllUniversitiesFromXLSX(filepath);
        String universityListJsonString = JsonUtil.universityListToJson(sourceUniversityList);
        System.out.println(universityListJsonString);
        System.out.println("----------");

        System.out.println("Deserialize Json String into List of University");
        deserializedUniversityList = JsonUtil.jsonToUniversityList(universityListJsonString);
        System.out.println(deserializedUniversityList);
        System.out.println("----------");
    }

    private static void studentListSerializeAndDeserialize(Path filepath, XLSXParser xlsxParser) {
        System.out.println("Serialize List of Student into Json String:");
        sourceStudentList = xlsxParser.getAllStudentsFromXLSX(filepath);
        String studentListJsonString = JsonUtil.studentListToJson(sourceStudentList);
        System.out.println(studentListJsonString);
        System.out.println("----------");

        System.out.println("Deserialize Json String into List of Student");
        deserializedStudentList = JsonUtil.jsonToStudentList(studentListJsonString);
        System.out.println(deserializedStudentList);
        System.out.println("----------");
    }

    private static void universitySerializeAndDeserialize() throws MalformedURLException {
        System.out.println("Serialize University into Json String:");
        University university = new University.Builder()
                .withId("01")
                .withFullName("abc")
                .withShortName("ABC")
                .withYearOfFoundation(1000)
                .withMainProfile(StudyProfile.CHEMISTRY)
                .withWebsite(new URL("https://abc.ru"))
                .build();
        String universityJsonString = JsonUtil.universityToJson(university);
        System.out.println(universityJsonString);
        System.out.println("----------");

        System.out.println("Deserialize Json String into University:");
        University university1 = JsonUtil.jsonToUniversity(universityJsonString);
        System.out.println(university1);
        System.out.println("----------");
    }

    private static void studentSerializeAndDeserialize() {
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
    }
}
