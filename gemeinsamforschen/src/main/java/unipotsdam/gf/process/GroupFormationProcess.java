package unipotsdam.gf.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupData;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.learninggoals.CompBaseMatcher;
import unipotsdam.gf.modules.group.learninggoals.PreferenceData;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserProfile;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.List;

@Singleton
public class GroupFormationProcess {

    @Inject
    ProjectDAO projectDAO;

    @Inject
    TaskDAO taskDAO;

    @Inject
    ProfileDAO profileDAO;

    @Inject
    private IGroupFinding groupfinding;

    @Inject
    private GroupDAO groupdao;

    @Inject
    private ICommunication iCommunication;


    /**
     * changes the group formation algorithm to the given.
     *
     * @param groupFormationMechanism
     * @param project
     */
    public void setGroupFormationMechanism(GroupFormationMechanism groupFormationMechanism, Project project) {
        projectDAO.setGroupFormationMechanism(groupFormationMechanism, project);
    }

    // taskDAO.persistTeacherTask(project, TaskName.FORM_GROUPS_MANUALLY, Phase.GroupFormation);

    /**
     * this method can only be called to change the group formation to manual groups or single
     *
     * @param groupFormationMechanism Manual or similarity of learning goals. others are going to be implemented
     * @param project                 which project
     */
    public void changeGroupFormationMechanism(GroupFormationMechanism groupFormationMechanism, Project project) {
        projectDAO.changeGroupFormationMechanism(groupFormationMechanism, project);
    }

    /**
     * this method is called if there are groups in the project
     * and if there groups are not handled manually
     * this method finalizes the groups
     *
     * @param project which project
     */
    public void finalize(Project project)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        taskDAO.persistTeacherTask(project, TaskName.CLOSE_GROUP_FINDING_PHASE, Phase.GroupFormation);
        Task task = new Task(TaskName.CLOSE_GROUP_FINDING_PHASE, new User(project.getAuthorEmail()), project,
                Progress.FINISHED);
        taskDAO.updateForUser(task);
        // Der Dozent muss nicht mehr auf weitere Studierende warten
        Task task2 =
                new Task(TaskName.WAIT_FOR_PARTICPANTS, new User(project.getAuthorEmail()), project, Progress.FINISHED);
        taskDAO.updateForUser(task2);
        Task task3 =
                new Task(TaskName.EDIT_FORMED_GROUPS, new User(project.getAuthorEmail()), project, Progress.FINISHED);
        taskDAO.updateForUser(task3);
        // Die Studierenden müssen nicht mehr auf die Gruppenfindung warten
        taskDAO.finishMemberTask(project, TaskName.WAITING_FOR_GROUP);
        taskDAO.persistMemberTask(project, TaskName.CONTACT_GROUP_MEMBERS, Phase.GroupFormation);
        project.setGroupWorkContext(profileDAO.getGroupWorkContext(project));

        saveGroups(getOrInitializeGroups(project).getGroups(), project);
        //if the project is finalized create group chat room
        List<Group> groups = groupfinding.getGroups(project);
        for (Group group : groups) {
            iCommunication.createChatRoom(group, false);
        }


    }


    /**
     * if there are no groups for project yet they are created via the gfm
     *
     * @param project
     * @return
     * @throws WrongNumberOfParticipantsException
     * @throws JAXBException
     * @throws JsonProcessingException
     */
    public GroupData getOrInitializeGroups(Project project)
            throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        List<Group> groups1 = groupfinding.getGroups(project);
        Integer participantCount = projectDAO.getParticipantCount(project).getParticipants();
        Integer groupMemberCount = 0;       //if participant authenticates after groups were built
        for (Group g : groups1) {            //groups need to be recalculated
            groupMemberCount += g.getMembers().size();
        }
        if (groups1 == null || groups1.isEmpty() || !groupMemberCount.equals(participantCount)) {
            List<Group> groups = groupfinding.getGroupFormationAlgorithm(project).calculateGroups(project);
            groupfinding.persistGroups(groups, project);
            groupfinding.persistOriginalGroups(groups, project, groupdao.getGroupFormationMechanism(project));
            taskDAO.persistTeacherTask(project, TaskName.CLOSE_GROUP_FINDING_PHASE, Phase.GroupFormation);
            return new GroupData(groups);
        } else {
            return new GroupData(groups1);
        }
    }

    /**
     * overwrites existing groups with manual selection
     *
     * @param groups
     * @param project
     * @throws RocketChatDownException
     * @throws UserDoesNotExistInRocketChatException
     */
    public void saveGroups(List<Group> groups, Project project)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        groupfinding.deleteGroups(project);
        groupfinding.persistGroups(groups, project);
    }

    public GroupFormationMechanism getGFMByProject(Project project) {
        return groupfinding.getGroupFormationMechanism(project);
    }

    /**
     * COMPBASE data for group matching alg 1
     * @param preferenceData
     * @throws Exception
     */
    public void sendCompBaseUserData(Project project, User user, PreferenceData preferenceData)
            throws Exception {
        new CompBaseMatcher().sendPreferenceData(project.getName(), user.getEmail(), preferenceData);
    }

    /**
     * make sure that keys in this match existing profilequestion ids (current range 1-28)
     * @param data
     * @param user
     * @param project
     */
    public void sendGroupAlDataToServer(HashMap<String, String> data, User user, Project project) {
        UserProfile userProfile = new UserProfile(data, user, project.getName());
        profileDAO.save(userProfile, null);
    }
}
