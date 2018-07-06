package unipotsdam.gf.core.states;

import unipotsdam.gf.core.management.user.User;

public class DosserUploadTask extends Task {
    public DosserUploadTask(User owner) {
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
