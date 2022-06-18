package ru.sf.models;

import com.google.gson.annotations.SerializedName;
import ru.sf.App;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public int getCurrentCourseNumber() {
        return currentCourseNumber;
    }

    public void setCurrentCourseNumber(int currentCourseNumber) {
        this.currentCourseNumber = currentCourseNumber;
    }

    public float getAvgExamScore() {
        return avgExamScore;
    }

    public void setAvgExamScore(float avgExamScore) {
        this.avgExamScore = avgExamScore;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    @Override
    public String toString() {
        return "Student [full_name=" + fullName + ", university_id=" + universityId +
                ", current_course_number=" + currentCourseNumber + ", average_exam-score=" + avgExamScore +
                ", date_of_birth=" + (dateOfBirth == null ? "нет данных" : dateOfBirth.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))) + "]";
    }
}
