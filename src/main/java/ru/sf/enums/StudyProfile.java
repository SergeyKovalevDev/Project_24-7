package ru.sf.enums;

public enum StudyProfile {
    MATHEMATICS ("Математика"),
    MEDICINE ("Медицина"),
    PHYSICS ("Физика"),
    CHEMISTRY ("Химия");

    final String profileName;

    StudyProfile(String profileName) {
        this.profileName = profileName;
    }

    @Override
    public String toString() {
        return profileName;
    }
}
