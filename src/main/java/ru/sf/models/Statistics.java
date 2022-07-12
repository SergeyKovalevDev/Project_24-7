package ru.sf.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sf.enums.StudyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Statistics {

    @XmlElement(name = "universityProfile")
    private StudyProfile mainProfile;

    @XmlElement(name = "avgScore")
    private float avgExamScore;

    @XmlTransient
    private long numberOfStudents;

    @XmlTransient
    private long numberOfUniversities;

    @XmlTransient
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
