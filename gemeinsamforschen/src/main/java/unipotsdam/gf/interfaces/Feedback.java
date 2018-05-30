package unipotsdam.gf.interfaces;

/**
PeerFeedback Interface
 */

public interface Feedback {

    /**
     * create Peer2PeerFeedback Object
     *
     * @param FeedbackUser: The student who is creating the feedback
     * @param SelectedStudent: The student who receive the feedback
     * @param Document: The selected document to give feedback about
     * @return Returns the Peer2PeerFeedback Object
     */

    Object createPeer2PeerFeedbackmask(Object FeedbackUser, Object SelectedStudent, Object Document);

    /**
     * give Peer2PeerFeedback
     *
     * @param Peer2PeerFeedback: The Peer2PeerFeedback as an Object
     * @param Document: The selected document
     * @return Send feedback with doc
     */

    Object giveFeedback(Object Peer2PeerFeedback, Object Document);

    /**
     * show Feedbackhistory
     *
     * @param Peer2PeerFeedback: The Peer2PeerFeedback as an Object
     * @param Document: The selected document
     * @return List of Feedbacks with Docs
     */

    Object showFeedback(Object Peer2PeerFeedback, Object Document);


}
