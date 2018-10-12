package unipotsdam.gf.process;

import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.phases.PhasesImpl;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class GroupFormationProcess {

    @Inject
    ProjectDAO projectDAO;

    @Inject
    TaskDAO taskDAO;



    @Inject
    private DossierCreationProcess dossierCreationProcess;

    @Inject
    private IGroupFinding groupfinding;

    public void setGroupFormationMechanism(GroupFormationMechanism groupFormationMechanism, Project project) {
            projectDAO.setGroupFormationMechanism(groupFormationMechanism, project);
    }


    // taskDAO.persistTeacherTask(project, TaskName.FORM_GROUPS_MANUALLY, Phase.GroupFormation);

    /**
     * this method can only be called to change the group formation to manual groups or single
     * @param groupFormationMechanism
     * @param project
     */
    public void changeGroupFormationMechanism(GroupFormationMechanism groupFormationMechanism, Project project) {
        projectDAO.changeGroupFormationMechanism(groupFormationMechanism, project);
    }

    /**
     * this method is called if there are groups in the project
     * and if there groups are not handled manually
     * this method finalizes the groups
     * @param project
     * @param groups
     */
    public void finalizeGroups( Project project, Group ... groups) {
        groupfinding.persistGroups(Arrays.asList(groups), project);
        taskDAO.persistTeacherTask(project, TaskName.CLOSE_GROUP_FINDING_PHASE, Phase.GroupFormation);
        taskDAO.persistMemberTask(project,  TaskName.CONTACT_GROUP_MEMBERS, Phase.GroupFormation);
    }


}
