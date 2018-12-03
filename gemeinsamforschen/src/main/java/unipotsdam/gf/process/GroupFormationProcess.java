package unipotsdam.gf.process;

import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupData;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class GroupFormationProcess {

    @Inject
    ProjectDAO projectDAO;

    @Inject
    TaskDAO taskDAO;

    @Inject
    UserDAO userDAO;

    @Inject
    private IGroupFinding groupfinding;

    public void setGroupFormationMechanism(GroupFormationMechanism groupFormationMechanism, Project project) {
            projectDAO.setGroupFormationMechanism(groupFormationMechanism, project);
    }

    // taskDAO.persistTeacherTask(project, TaskName.FORM_GROUPS_MANUALLY, Phase.GroupFormation);
    /**
     * this method can only be called to change the group formation to manual groups or single
     * @param groupFormationMechanism Manual or similarity of learning goals. others are going to be implemented
     * @param project which project
     */
    public void changeGroupFormationMechanism(GroupFormationMechanism groupFormationMechanism, Project project) {
        projectDAO.changeGroupFormationMechanism(groupFormationMechanism, project);
    }

    /**
     * this method is called if there are groups in the project
     * and if there groups are not handled manually
     * this method finalizes the groups
     * @param project which project
     */
    public void finalize(Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        taskDAO.persistTeacherTask(project, TaskName.CLOSE_GROUP_FINDING_PHASE, Phase.GroupFormation);
        Task task = new Task(TaskName.CLOSE_GROUP_FINDING_PHASE, project.getAuthorEmail(), project.getName(), Progress.FINISHED);
        taskDAO.updateForUser(task);
        // Der Dozent muss nicht mehr auf weitere Studierende warten
        Task task2 = new Task(TaskName.WAIT_FOR_PARTICPANTS, project.getAuthorEmail(), project.getName(), Progress
                .FINISHED);
        taskDAO.updateForUser(task2);
        // Die Studierenden müssen nicht mehr auf die Gruppenfindung warten
        taskDAO.finishMemberTask(project, TaskName.WAITING_FOR_GROUP);
        taskDAO.persistMemberTask(project,  TaskName.CONTACT_GROUP_MEMBERS, Phase.GroupFormation);

        //if the project is finalized create group chat room
        groupfinding.finalizeGroups(project);
    }


    public GroupData getOrInitializeGroups(Project project) {
        List<Group> groups = groupfinding.getGroups(project);
        if (groups.isEmpty()) {
            if (groupfinding.getGFM(project).equals(GroupFormationMechanism.Manual)) {
                List<User> users = userDAO.getUsersByProjectName(project.getName());
                Group group = new Group(users, project.getName());
                groups.add(group);
            }else{
                groups = groupfinding.createRandomGroups(project);
            }

        }
        return new GroupData(groups);
    }

    public void saveGroups(List<Group> groups,Project project) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        groupfinding.deleteGroups(project);
        groupfinding.persistGroups(groups, project);
        finalize(project);
    }
}
