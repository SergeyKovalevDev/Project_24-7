package ru.sf.models;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sf.App;

import java.time.LocalDate;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class Student {

    @SerializedName("ФИО студента")
    private String fullName;
    @SerializedName("Код университета")
    private String universityId;
    @SerializedName("Номер курса")
    private int currentCourseNumber;
    @SerializedName("Средний балл")
    private float avgExamScore;
    @SerializedName("Дата рождения")
    private LocalDate dateOfBirth;

    private Student(Builder builder) {
        setFullName(builder.fullName);
        setUniversityId(builder.universityId);
        setCurrentCourseNumber(builder.currentCourseNumber);
        setAvgExamScore(builder.avgExamScore);
        setDateOfBirth(builder.dateOfBirth);
    }

    public static final class Builder {
        private String fullName;
        private String universityId;
        private int currentCourseNumber;
        private float avgExamScore;
        private LocalDate dateOfBirth;

        public Builder() {
        }

        public Builder withFullName(String val) {
            fullName = val;
            return this;
        }

        public Builder withUniversityId(String val) {
            universityId = val;
            return this;
        }

        public Builder withCurrentCourseNumber(int val) {
            currentCourseNumber = val;
            return this;
        }

        public Builder withAvgExamScore(float val) {
            avgExamScore = val;
            return this;
        }

        public Builder withDateOfBirth(LocalDate val) {
            dateOfBirth = val;
            return this;
        }

        public Student build() {
            return validateStudent() ? new Student(this) : null;
        }

        private boolean validateStudent() {
            int acceptableAge = Integer.parseInt(App.properties.getProperty("ACCEPTABLE_AGE_OF_STUDENTS_IN_YEARS"));
            boolean dateOfBirthValid = true;
            if (dateOfBirth != null) dateOfBirthValid = dateOfBirth.isBefore(LocalDate.now().minusYears(acceptableAge));
            return (fullName != null && !fullName.trim().isEmpty() &&
                    universityId != null && !universityId.trim().isEmpty() &&
                    currentCourseNumber > 0 &&
                    avgExamScore >= 0.0f &&
                    dateOfBirthValid);
        }
    }
}
