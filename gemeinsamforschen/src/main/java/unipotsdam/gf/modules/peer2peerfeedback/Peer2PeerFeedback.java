package unipotsdam.gf.modules.peer2peerfeedback;

import unipotsdam.gf.core.management.user.User;

import java.io.File;

/**
 Peer2PeerFeedback Object
 */

public class Peer2PeerFeedback{


    String feedbacktopic;
    String feedbacktype;
    String feedbackreference;
    File document;
    String feedbacktemplate;
    User feedbacksender;
    User feedbackreceiver;

    public Peer2PeerFeedback(String feedbacktopic, String feedbacktype, String feedbackreference, File document, String feedbacktemplate, User feedbacksender, User feedbackreceiver) {
        this.feedbacktopic = feedbacktopic;
        this.feedbacktype = feedbacktype;
        this.feedbackreference = feedbackreference;
        this.document = document;
        this.feedbacktemplate = feedbacktemplate;
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


    public String getFeedbackreference() {

        return feedbackreference;
    }

    public void setFeedbackreference(String feedbackreference) {

        this.feedbackreference = feedbackreference;
    }


    public File getDocument() {

        return document;
    }

    public void setDocument(File document) {

        this.document = document;
    }

    public String getFeedbacktemplate() {

        return feedbacktemplate;
    }

    public void setFeedbacktemplate(String feedbacktemplate) {

        this.feedbacktemplate = feedbacktemplate;
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
                ", feedbackreference=" + feedbackreference +
                ", feedbacktemplate=" + feedbacktemplate +
                ", feedbacksender='" + feedbacksender +
                ", feedbackreceiver=" + feedbackreceiver +
                ", document=" + document.toString() +
                '}';
    }


}