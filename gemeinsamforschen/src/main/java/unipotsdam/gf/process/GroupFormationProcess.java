package unipotsdam.gf.process;

import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.util.Arrays;

public class GroupFormationProcess {

    @Inject
    ProjectDAO projectDAO;

    @Inject
    TaskDAO taskDAO;

    @Inject
    private IGroupFinding groupfinding;

    public void changeGroupFormationMechanism(GroupFormationMechanism groupFormationMechanism, Project project) {
        projectDAO.changeGroupFormationMechanism(groupFormationMechanism, project);
        taskDAO.persistTeacherTask(project, TaskName.FORM_GROUPS_MANUALLY, Phase.GroupFormation);
    }

    public void finalizeGroups(Group[] groups, Project project) {
        groupfinding.persistGroups(Arrays.asList(groups), project);
        taskDAO.persistTeacherTask(project, TaskName.CLOSE_GROUP_FINDING_PHASE, Phase.GroupFormation);
        taskDAO.persistMemberTask(project,  TaskName.CONTACT_GROUP_MEMBERS, Phase.GroupFormation);
    }
}
