package unipotsdam.gf.modules.journal.model;

import unipotsdam.gf.modules.assessment.controller.StudentIdentifier;

public class Journal {

    StudentIdentifier studentIdentifier;
    String entry;
    long timestamp;
    Visibility visibility;
    String category; //TODO enum

    public Journal() {}

    public Journal(StudentIdentifier studentIdentifier, String entry, long timestamp, Visibility visibility, String category) {
        this.studentIdentifier = studentIdentifier;
        this.entry = entry;
        this.timestamp = timestamp;
        this.visibility = visibility;
        this.category = category;
    }

    public StudentIdentifier getStudentIdentifier() {
        return studentIdentifier;
    }

    public void setStudentIdentifier(StudentIdentifier studentIdentifier) {
        this.studentIdentifier = studentIdentifier;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "studentIdentifier=" + studentIdentifier +
                ", entry='" + entry + '\'' +
                ", timestamp=" + timestamp +
                ", visibility=" + visibility +
                ", category='" + category + '\'' +
                '}';
    }
}
