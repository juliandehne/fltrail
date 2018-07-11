package unipotsdam.gf.modules.journal.model;


import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import static unipotsdam.gf.view.MarkdownUtils.convertMarkdownToHtml;

/**
 * Model Class for the learnig journal of the e-portfolio
 */
public class Journal {

    private long id;
    private StudentIdentifier studentIdentifier;
    private String creator;
    private String entryHTML;
    private String entryMD;
    private long timestamp;
    private Visibility visibility;
    private String category;//TODO enum

    public Journal() {}

    public Journal(long id, StudentIdentifier studentIdentifier, String entry, long timestamp, Visibility visibility, String category) {
        this.id = id;
        this.studentIdentifier = studentIdentifier;
        // TODO setName per StudentID
        this.entryHTML = convertMarkdownToHtml(entry);
        this.entryMD = entry;
        this.timestamp = timestamp;
        this.visibility = visibility;
        this.category = category;
    }

    public void setEntry(String entry){
        this.entryMD = entry;
        this.entryHTML = convertMarkdownToHtml(entry);
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

    public String getEntryHTML() {
        return entryHTML;
    }

    public void setEntryHTML(String entryHTML) {
        this.entryHTML = entryHTML;
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

    public String getEntryMD() {
        return entryMD;
    }

    public void setEntryMD(String entryMD) {
        this.entryMD = entryMD;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "id=" + id +
                ", studentIdentifier=" + studentIdentifier +
                ", creator='" + creator + '\'' +
                ", entryHTML='" + entryHTML + '\'' +
                ", timestamp=" + timestamp +
                ", visibility=" + visibility +
                ", category='" + category + '\'' +
                '}';
    }
}
