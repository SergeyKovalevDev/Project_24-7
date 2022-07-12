package ru.sf.models;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sf.enums.StudyProfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.net.URL;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class University {

    @SerializedName("Код университета")
    @XmlElement(name = "universityId")
    private String id;

    @SerializedName("Полное название")
    @XmlElement(name = "universityName")
    private String fullName;

    @SerializedName("Сокращенное название")
    @XmlTransient
    private String shortName;

    @SerializedName("Год основания")
    @XmlTransient
    private int yearOfFoundation;

    @SerializedName("Профиль")
    @XmlElement(name = "universityProfile")
    private StudyProfile mainProfile;

    @SerializedName("Сайт")
    @XmlTransient
    private URL website;

    private University(Builder builder) {
        setId(builder.id);
        setFullName(builder.fullName);
        setShortName(builder.shortName);
        setYearOfFoundation(builder.yearOfFoundation);
        setMainProfile(builder.mainProfile);
        setWebsite(builder.website);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getYearOfFoundation() {
        return yearOfFoundation;
    }

    public void setYearOfFoundation(int yearOfFoundation) {
        this.yearOfFoundation = yearOfFoundation;
    }

    public StudyProfile getMainProfile() {
        return mainProfile;
    }

    public void setMainProfile(StudyProfile mainProfile) {
        this.mainProfile = mainProfile;
    }

    public URL getWebsite() {
        return website;
    }

    public void setWebsite(URL website) {
        this.website = website;
    }

    public static final class Builder {
        private String id;
        private String fullName;
        private String shortName;
        private int yearOfFoundation;
        private StudyProfile mainProfile;
        private URL website;

        public Builder() {
        }

        public Builder withId(String val) {
            id = val;
            return this;
        }

        public Builder withFullName(String val) {
            fullName = val;
            return this;
        }

        public Builder withShortName(String val) {
            shortName = val;
            return this;
        }

        public Builder withYearOfFoundation(int val) {
            yearOfFoundation = val;
            return this;
        }

        public Builder withMainProfile(StudyProfile val) {
            mainProfile = val;
            return this;
        }

        public Builder withWebsite(URL val) {
            website = val;
            return this;
        }

        public University build() {
            return validateUniversity() ? new University(this) : null;
        }

        public boolean validateUniversity() {
            return  id != null && !id.trim().isEmpty() &&
                    fullName != null && !fullName.trim().isEmpty() &&
                    shortName != null && !shortName.trim().isEmpty() &&
                    yearOfFoundation < LocalDateTime.now().getYear() &&
                    mainProfile != null;
        }
    }
}
