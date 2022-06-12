package ru.sf.models;

import ru.sf.App;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Student {

    private String fullName;
    private String universityId;
    private int currentCourseNumber;
    private float avgExamScore;
    private LocalDateTime dateOfBirth;

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

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public static final class Builder {
        private String fullName;
        private String universityId;
        private int currentCourseNumber;
        private float avgExamScore;
        private LocalDateTime dateOfBirth;

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

        public Builder withDateOfBirth(LocalDateTime val) {
            dateOfBirth = val;
            return this;
        }

        public Student build() {
            return validateStudent() ? new Student(this) : null;
        }

        private boolean validateStudent() {
            int acceptableAge = Integer.parseInt(App.properties.getProperty("ACCEPTABLE_AGE_OF_STUDENTS_IN_YEARS"));
            boolean dateOfBirthValid = true;
            if (dateOfBirth != null) dateOfBirthValid = dateOfBirth.isBefore(LocalDateTime.now().minusYears(acceptableAge));
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
