
/**
 PeerFeedback Object
 */

public class Peer2PeerFeedback{


    String feedbacktopic;
    String feedbacktype;
    String feedbackreference;
   // Student feedbackreceiver; //StudentIdentifier?
   // Student feedbacksender; //StudentIdentifier?
    File document;
    String feedbacktemplate;

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

/**
    public Student getFeedbackreceiver() {

        return feedbackreceiver;
    }

    public void setFeedbackreceiver(Student feedbackreceiver) {

        this.feedbackreceiver = feedbackreceiver;
    }

    public Student getFeedbacksender() {

        return feedbacksender;
    }

    public void setFeedbacksender(Student feedbacksender) {

        this.feedbacksender = feedbacksender;
    }

 */

    public File getDocument() {

        return document;
    }

    public void setDocument(Document document) {

        this.document = document;
    }

    public String getFeedbacktemplate() {

        return feedbacktemplate;
    }

    public void setFeedbacktemplate(String feedbacktemplate) {

        this.feedbacktemplate = feedbacktemplate;
    }


}