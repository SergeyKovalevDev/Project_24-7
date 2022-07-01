package ru.sf.utils;

import ru.sf.enums.StudyProfile;
import ru.sf.models.Statistics;
import ru.sf.models.Student;
import ru.sf.models.University;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class StatisticBuilder {

    public static List<Statistics> getStatistic(List<Student> studentList, List<University> universityList) {
        return Stream.of(StudyProfile.values())
                .map(studyProfile -> universityList.stream()
                        .filter(university -> university.getMainProfile().equals(studyProfile))
                        .toList())
                .filter(list -> list.size() >= 1)
                .map(filteredUniversityList -> buildStatistics(filteredUniversityList, studentList))
                .toList();
    }

    private static Statistics buildStatistics(List<University> universityList, List<Student> studentList) {
        return new Statistics.Builder()
                .withMainProfile(getMainProfile(universityList))
                .withAvgExamScore(getAvgExamScore(universityList, studentList))
                .withNumberOfStudentsByProfile(getNumberOfStudents(universityList, studentList))
                .withNumberOfUniversitiesByProfile(getNumberOfUniversities(universityList))
                .withUniversityName(getUniversityNameList(universityList))
                .build();
    }

    private static StudyProfile getMainProfile(List<University> universityList) {
        return universityList.get(0).getMainProfile();
    }

    private static float getAvgExamScore(List<University> universityList, List<Student> studentList) {
        return doubleToFloatRound(universityList.stream()
                .map(university -> studentList.stream()
                        .filter(student -> student.getUniversityId().equals(university.getId()))
                        .toList())
                .flatMap(Collection::stream)
                .mapToDouble(Student::getAvgExamScore)
                .average()
                .orElse(Double.NaN));
    }

    private static float doubleToFloatRound(Double value) {
        return value.isNaN() ? 0f : BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).floatValue();
    }

    private static long getNumberOfStudents(List<University> universityList, List<Student> studentList) {
        return universityList.stream()
                .map(university -> studentList.stream()
                        .filter(student -> student.getUniversityId().equals(university.getId()))
                        .toList())
                .mapToLong(Collection::size)
                .sum();
    }

    private static long getNumberOfUniversities(List<University> universityList) {
        return universityList.size();
    }

    private static List<String> getUniversityNameList(List<University> universityList) {
        return universityList.stream()
                .map(University::getShortName)
                .toList();
    }
}
