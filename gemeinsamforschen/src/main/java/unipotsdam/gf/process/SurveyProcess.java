package unipotsdam.gf.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.config.GroupAlConfig;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.communication.Messages;
import unipotsdam.gf.modules.communication.model.EMailMessage;
import unipotsdam.gf.modules.communication.service.EmailService;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.preferences.survey.*;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.scheduler.Scheduler;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.List;

public class SurveyProcess {

    @Inject
    private UserDAO userDAO;

    @Inject
    private IPhases phases;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private SurveyMapper surveyMapper;

    @Inject
    private EmailService emailService;

    @Inject
    private Management iManagement;

    @Inject
    private IGroupFinding groupfinding;


    public SurveyData getSurveyQuestions(String projectId) throws Exception {
        Project project = projectDAO.getProjectByName(projectId);
        GroupWorkContext groupWorkContext = surveyMapper.getGroupWorkContext(project);
        return surveyMapper.getItemsFromDB(groupWorkContext, project);
    }


    public synchronized void saveSurveyData(Project project, HashMap<String, String> data, HttpServletRequest req)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        GroupWorkContext groupWorkContext = surveyMapper.getGroupWorkContext(project);
        if (GroupWorkContextUtil.isSurveyContext(groupWorkContext)) {
            surveyMapper.saveData(data, project, req);
            if (GroupWorkContextUtil.isAutomatedGroupFormation(groupWorkContext)) {
                List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
                if (usersByProjectName.size() == GroupAlConfig.GROUPAL_SURVEY_COHORT_SIZE) {
                    List<Group> groups = groupfinding.getGroupFormationAlgorithm(project).calculateGroups(project);
                    groupfinding.persistGroups(groups, project);
                    phases.endPhase(Phase.GroupFormation, project);

                /*for (User user : usersByProjectName) {
                    EMailMessage message = Messages.SurveyGroupFormation(project, user.getEmail());
                    emailService.sendSingleMessage(message, user);
                }*/

                    // schedule an group email, that in 30 days from having finished group formation
                    // an evaluation email is send out
                /*if(GroupWorkContextUtil.isSurveyContext(groupWorkContext)) {
                    Scheduler scheduler = new Scheduler(project, emailService);
                    scheduler.start(sce);
                }*/
                }
            }
            //}
        } else {
            surveyMapper.saveData(data, project, req);
        }

    }

    public SurveyProject getOrCreateSurveyProject(GroupWorkContext projectContext) {
        SurveyProject surveyProject = projectDAO.getActiveSurveyProject(projectContext);
        if (surveyProject == null) {
            // if result is empty create new project, add all the questions to it and return this
            SurveyProject project = surveyMapper.createNewProject(projectContext);
            projectDAO.setGroupFormationMechanism(GroupFormationMechanism.UserProfilStrategy, project);
            return project;
        }
        return surveyProject;
    }

    public Boolean isStudentInProject(User user) {
        List<Project> projects = iManagement.getProjectsStudent(user);
        return projects != null && !projects.isEmpty();
    }

    public List<Group> startGroupFormation(String projectName) throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        Project project = new Project(projectName);
        // todo set GroupWorkContext
        groupfinding.deleteGroups(project);
        List<Group> groups = groupfinding.getGroupFormationAlgorithm(project).calculateGroups(project);
        groupfinding.persistGroups(groups, project);
        return groups;
    }

}
