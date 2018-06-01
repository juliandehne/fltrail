package unipotsdam.gf.modules.journal;

import unipotsdam.gf.interfaces.IJournal;

/**
 * Prototype of JournalEntry Class
 */
public class JournalEntry {

    long id;
    long owner;
    long project;
    long timestamp;
    IJournal.Visibility visibility;
    String text;


    public JournalEntry() {
    }

    public JournalEntry(long id, long owner, long project, long timestamp, IJournal.Visibility visibility, String text) {
        this.id = id;
        this.owner = owner;
        this.project = project;
        this.timestamp = timestamp;
        this.visibility = visibility;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public long getProject() {
        return project;
    }

    public void setProject(long project) {
        this.project = project;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public IJournal.Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(IJournal.Visibility visibility) {
        this.visibility = visibility;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "JournalEntry{" +
                "id=" + id +
                ", owner=" + owner +
                ", project=" + project +
                ", timestamp=" + timestamp +
                ", visibility=" + visibility +
                ", text='" + text + '\'' +
                '}';
    }
}
