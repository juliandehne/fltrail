package unipotsdam.gf.modules.peer2peerfeedback.peerfeedback.Model;

import unipotsdam.gf.modules.peer2peerfeedback.Category;

import static unipotsdam.gf.view.MarkdownUtils.convertMarkdownToHtml;

/**
 * Peer2PeerFeedback Object
 * created by Katharina
 */

public class Peer2PeerFeedback {


    private String id;
    private long timestamp;
    private Category category;
    private String text;
    private String sender;
    private String receiver;
    private String filename;

    public Peer2PeerFeedback(String id, long timestamp, Category category, String text, String sender, String receiver, String filename) {
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

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

    public void setFeedbacksender(String feedbacksender) {
        this.sender = sender;
    }

    public String getFeedbackreceiver() {
        return receiver;
    }

    public void setFeedbackreceiver(String feedbackreceiver) {
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