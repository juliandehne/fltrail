package unipotsdam.gf.core.states;

import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.modules.communication.model.Message;

import javax.inject.Inject;

public class States {

    @Inject
    ICommunication iCommunication;


    public void endPhase(ProjectPhase currentPhase) {
        // TODO implement

        // calculate reaction

        // if no problem change phase

        // if problem send message

        // and start recovery process
    }


    private void sendProblemMessage(String message, User user) {
        iCommunication.sendSingleMessage(new Message(null, message),user);
    }




}
