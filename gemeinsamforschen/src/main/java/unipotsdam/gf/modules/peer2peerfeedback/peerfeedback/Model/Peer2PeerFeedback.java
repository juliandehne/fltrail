package unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Model;

import unipotsdam.gf.modules.peer2peerfeedback.Category;

import static unipotsdam.gf.view.MarkdownUtils.convertMarkdownToHtml;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Peer2PeerFeedback Object
 * created by Katharina
 */

public class Peer2PeerFeedback {


    private String id;
    private Timestamp timestamp;
    private Category category;
    private String text;
    private String sender;
    private String receiver;
    private String filename;

    public Peer2PeerFeedback(String id, Timestamp timestamp, Category category, String text, String sender, String receiver, String filename) {
        this.id = id;
        this.timestamp = timestamp;
        this.category = category;
        this.text = convertMarkdownToHtml(text);
        this.sender = sender;
        this.receiver = receiver;
        this.filename = filename;
    }

    public Peer2PeerFeedback() {

    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public Timestamp getTimestamp() { return timestamp; }


    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }


    public Category getFeedbackcategory() {
        return category;
    }

    public void setFeedbackcategory(Category category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFeedbacksender() {
        return sender;
    }

    public void setFeedbacksender(String sender) {
        this.sender = sender;
    }

    public String getFeedbackreceiver() {
        return receiver;
    }

    public void setFeedbackreceiver(String receiver) {
        this.receiver = receiver;
    }


    @Override
    public String toString() {
        return "Peer2PeerFeedback{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", category=" + category +
                ", sender='" + sender +
                ", receiver=" + receiver +
                ", text=" + text +
                ", filename=" + filename +
                '}';
    }


}