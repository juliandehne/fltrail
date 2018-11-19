package unipotsdam.gf.modules.journal.model;


import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.annotation.model.Category;

import java.util.Date;

import static unipotsdam.gf.util.MarkdownUtils.convertMarkdownToHtml;

/**
 * Model Class for the learning journal of the e-portfolio
 */
public class Journal {

    private String id;
    private StudentIdentifier studentIdentifier;
    private String entryHTML;
    private String entryMD;
    private long timestamp;
    private Visibility visibility;
    private Category category;
    private boolean open;

    public Journal() {}

    public Journal(String id, StudentIdentifier studentIdentifier, String entryMD, Visibility visibility, Category category) {
        this.id = id;
        this.studentIdentifier = studentIdentifier;
        entryHTML = convertMarkdownToHtml(entryMD);
        this.entryMD = entryMD;
        this.visibility = visibility;
        this.category = category;
        open = true;
        timestamp = new Date().getTime();
    }

    public Journal(String id, StudentIdentifier studentIdentifier, String entryMD, long timestamp, Visibility visibility, Category category, boolean open) {
        this.id = id;
        this.studentIdentifier = studentIdentifier;
        entryHTML = convertMarkdownToHtml(entryMD);
        this.entryMD = entryMD;
        this.timestamp = timestamp;
        this.visibility = visibility;
        this.category = category;
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setEntry(String entry){
        entryMD = entry;
        entryHTML = convertMarkdownToHtml(entry);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StudentIdentifier getStudentIdentifier() {
        return studentIdentifier;
    }

    public void setStudentIdentifier(StudentIdentifier userNameentifier) {
        this.studentIdentifier = userNameentifier;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "id=" + id +
                ", studentIdentifier=" + studentIdentifier +
                ", entryHTML='" + entryHTML + '\'' +
                ", entryMD='" + entryMD + '\'' +
                ", timestamp=" + timestamp +
                ", visibility=" + visibility +
                ", category=" + category +
                ", open=" + open +
                '}';
    }

    public String getEntryMD() {
        return entryMD;
    }

    public void setEntryMD(String entryMD) {
        this.entryMD = entryMD;
    }

}
