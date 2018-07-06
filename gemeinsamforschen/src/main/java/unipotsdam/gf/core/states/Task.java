package unipotsdam.gf.core.states;

import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.Message;

import javax.inject.Inject;

public abstract class Task {

    @Inject
    ICommunication iCommunication;

    // the user who has to do the task
    protected User owner;

    public Task(User owner) {
        this.owner = owner;
    }

    public abstract String getTaskMessage();

    public void start() {
        sendTaskMessage();
        save();
    }

    private void save() {
        String name = getClass().getName(); // this returns the runtime name of the subclass i.e. PeerAssessmentTask
        String url = getTaskUrl();

    }

    /**
     * should be a relative path like
     *  /dossiers/upload
     *  /peerfeedback/{userId}/give
     *  /peerassessment/{userId}/give
     *  or similar
     *
     * @return
     */
    protected abstract String getTaskUrl();

    public void sendTaskMessage() {
        iCommunication.sendSingleMessage(new Message(null, getTaskMessage()), owner);
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
