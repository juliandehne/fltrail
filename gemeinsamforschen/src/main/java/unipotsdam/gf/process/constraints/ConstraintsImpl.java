package unipotsdam.gf.process.constraints;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.process.tasks.ParticipantsCount;

import javax.inject.Inject;

public class ConstraintsImpl {

    @Inject
    ProjectDAO projectDAO;

    @Inject
    IGroupFinding groupFinding;

    public Boolean checkIfGroupsCanBeFormed(Project project) {
        ParticipantsCount participantCount = projectDAO.getParticipantCount(project);
        int minNumberOfStudentsNeeded = groupFinding.getMinNumberOfStudentsNeeded(project);
        return participantCount.getParticipants() >= minNumberOfStudentsNeeded;
    }
}
