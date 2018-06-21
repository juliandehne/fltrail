package unipotsdam.gf.modules.peer2peerfeedback;

import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.utils.Category;

import java.io.File;

/**
 Peer2PeerFeedback Object
 created by Katharina
 */

public class Peer2PeerFeedback{


    private String feedbacktopic;
    private String feedbacktype;
    private Category feedbackcategory;
    private File document;
    private User feedbacksender;
    private User feedbackreceiver;

    public Peer2PeerFeedback(String feedbacktopic, String feedbacktype, Category feedbackcategory, File document, User feedbacksender, User feedbackreceiver) {
        this.feedbacktopic = feedbacktopic;
        this.feedbacktype = feedbacktype;
        this.feedbackcategory = feedbackcategory;
        this.document = document;
        this.feedbacksender = feedbacksender;
        this.feedbackreceiver = feedbackreceiver;
    }

    public String getFeedbacktopic() {

        return feedbacktopic;
    }

    public void setFeedbacktopic(String feedbacktopic) {

        this.feedbacktopic = feedbacktopic;
    }


    public String getFeedbacktype() {

        return feedbacktype;
    }

    public void setFeedbacktype(String feedbacktype) {

        this.feedbacktype = feedbacktype;
    }



    public Category getFeedbackcategory() {
        return feedbackcategory;
    }

    public void setFeedbackcategory(Category feedbackcategory) {
        this.feedbackcategory = feedbackcategory;
    }


    public File getDocument() {

        return document;
    }

    public void setDocument(File document) {

        this.document = document;
    }


    public User getFeedbacksender() {
        return feedbacksender;
    }

    public void setFeedbacksender(User feedbacksender) {
        this.feedbacksender = feedbacksender;
    }

    public User getFeedbackreceiver() {
        return feedbackreceiver;
    }

    public void setFeedbackreceiver(User feedbackreceiver) {
        this.feedbackreceiver = feedbackreceiver;
    }

    
    @Override
    public String toString() {
        return "Peer2PeerFeedback{" +
                "feedbacktopic=" + feedbacktopic +
                ", feedbacktype=" + feedbacktype +
                ", feedbackreference=" + feedbackcategory +
                ", feedbacksender='" + feedbacksender +
                ", feedbackreceiver=" + feedbackreceiver +
                ", document=" + document.toString() +
                '}';
    }


}