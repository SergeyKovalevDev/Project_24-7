package ru.sf.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sf.enums.StudyProfile;

import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class Statistics {

    private StudyProfile mainProfile;
    private float avgExamScore;
    private long numberOfStudents;
    private long numberOfUniversities;
    private List<String> universityName;

    private Statistics(Builder builder) {
        setMainProfile(builder.mainProfile);
        setAvgExamScore(builder.avgExamScore);
        setNumberOfStudents(builder.numberOfStudentsByProfile);
        setNumberOfUniversities(builder.numberOfUniversitiesByProfile);
        setUniversityName(builder.universityName);
    }

    public static final class Builder {
        private StudyProfile mainProfile;
        private float avgExamScore;
        private long numberOfStudentsByProfile;
        private long numberOfUniversitiesByProfile;
        private List<String> universityName;

        public Builder() {
        }

        public Builder withMainProfile(StudyProfile val) {
            mainProfile = val;
            return this;
        }

        public Builder withAvgExamScore(float val) {
            avgExamScore = val;
            return this;
        }

        public Builder withNumberOfStudentsByProfile(long val) {
            numberOfStudentsByProfile = val;
            return this;
        }

        public Builder withNumberOfUniversitiesByProfile(long val) {
            numberOfUniversitiesByProfile = val;
            return this;
        }

        public Builder withUniversityName(List<String> val) {
            universityName = val;
            return this;
        }

        public Statistics build() {
            return new Statistics(this);
        }
    }
}
