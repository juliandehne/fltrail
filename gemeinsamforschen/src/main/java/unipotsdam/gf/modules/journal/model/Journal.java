package unipotsdam.gf.modules.journal.model;

import unipotsdam.gf.modules.assessment.controller.StudentIdentifier;

/**
 * Model Class for the learnig journal of the e-portfolio
 */
public class Journal {

    long id;
    StudentIdentifier studentIdentifier;
    String creator;
    String entry;
    long timestamp;
    Visibility visibility;
    String category; //TODO enum

    public Journal() {}

    public Journal(long id, StudentIdentifier studentIdentifier, String entry, long timestamp, Visibility visibility, String category) {
        this.id = id;
        this.studentIdentifier = studentIdentifier;
        // TODO setName per StudentID
        this.entry = entry;
        this.timestamp = timestamp;
        this.visibility = visibility;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "id=" + id +
                ", studentIdentifier=" + studentIdentifier +
                ", creator='" + creator + '\'' +
                ", entry='" + entry + '\'' +
                ", timestamp=" + timestamp +
                ", visibility=" + visibility +
                ", category='" + category + '\'' +
                '}';
    }
}
