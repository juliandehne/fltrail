package unipotsdam.gf.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GroupAlConfig;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.communication.service.EmailService;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupFormationAlgorithm;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContextUtil;
import unipotsdam.gf.modules.group.preferences.survey.SurveyMapper;
import unipotsdam.gf.modules.group.preferences.survey.SurveyProject;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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

    private final static Logger log = LoggerFactory.getLogger(SurveyProcess.class);


    public synchronized void saveSurveyData(Project project, HashMap<String, String> data, HttpServletRequest req)
            throws Exception {
        surveyMapper.saveData(data, project, req);
    }

    public List<Group> formGroupsForSurvey(SurveyProject project) throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException, RocketChatDownException, UserDoesNotExistInRocketChatException {
        List<Group> groups1 = groupfinding.getGroups(project);
        if (!groups1.isEmpty()) {
            groupfinding.deleteGroups(project);
        }
        GroupFormationAlgorithm groupFormationAlgorithm = groupfinding.getGroupFormationAlgorithm(project);
        groups1 = groupFormationAlgorithm.calculateGroups(project);
        groupfinding.persistGroups(groups1, project);
        phases.saveState(project, Phase.Execution);
        sendEmailsForAutomatedGroupsFormed();
        return groups1;
    }

    private void sendEmailsForAutomatedGroupsFormed() {
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
        //}
    }

    public SurveyProject getSurveyProjectNameOrInitialize(GroupWorkContext projectContext, String email) throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException, RocketChatDownException, UserDoesNotExistInRocketChatException {

        if (!GroupWorkContextUtil.isGamingOrAutomatedGroupFormation(projectContext)) {
            log.debug("project is manual group formation and has only one name equal to context");
            return new SurveyProject(projectContext.toString(), projectContext);
        } else {
            SurveyProject surveyProject = projectDAO.getSurveyProjectByUser(new User(email));
            if (surveyProject == null) {
                log.debug("user has no project, checking for active");
                surveyProject = projectDAO.getActiveSurveyProject(projectContext);
                if (surveyProject == null) {
                    surveyProject = surveyMapper.createNewProject(projectContext);
                    log.info("created first project for context " + projectContext);
                }
            } else {
                log.debug("has a project");
                List<User> usersByProjectName = userDAO.getUsersByProjectName(surveyProject.getName());
                if (usersByProjectName.size() >= GroupAlConfig.GROUPAL_SURVEY_COHORT_SIZE &&
                        surveyProject.getPhase() == Phase.GroupFormation) {
                    log.debug("has a project and it has sufficient participants");
                    formGroupsForSurvey(surveyProject);
                    surveyMapper.createNewProject(projectContext);
                    log.info("created new project for context " + projectContext);
                }
            }
            return surveyProject;
        }
    }

    public Boolean isStudentInProject(User user) {
        List<Project> projects = iManagement.getProjectsStudent(user);
        return projects != null && !projects.isEmpty();
    }
}
