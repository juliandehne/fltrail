package unipotsdam.gf.modules.group.preferences.survey;

import unipotsdam.gf.modules.project.Project;

import java.util.HashMap;

public class ProjectStatus {

    HashMap<Project, Integer> numberOfParticipants;

    public ProjectStatus() {
    }

    public HashMap<Project, Integer> getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(
            HashMap<Project, Integer> numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

}
