package unipotsdam.gf.core.states;

import unipotsdam.gf.core.management.user.User;

public class PeerAssessmentTask extends Task {
    public PeerAssessmentTask(User owner) {
        super(owner);
    }

    @Override
    public String getTaskMessage() {
        return null;
    }

    @Override
    protected String getTaskUrl() {
        return null;
    }


}
