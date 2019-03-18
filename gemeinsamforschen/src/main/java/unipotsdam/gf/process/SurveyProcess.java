package unipotsdam.gf.process;

import unipotsdam.gf.config.GroupAlConfig;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.communication.service.EmailService;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupFormationAlgorithm;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContextUtil;
import unipotsdam.gf.modules.group.preferences.survey.SurveyMapper;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
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



    public synchronized void saveSurveyData(Project project, HashMap<String, String> data, HttpServletRequest req, GroupWorkContext groupWorkContext, ServletContextEvent sce)
            throws Exception {
            surveyMapper.saveData(data, project, req);
            if (GroupWorkContextUtil.isGamingOrAutomatedGroupFormation(groupWorkContext)) {
                List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
                if (usersByProjectName.size() >= GroupAlConfig.GROUPAL_SURVEY_COHORT_SIZE) {
                    GroupFormationAlgorithm groupFormationAlgorithm = groupfinding.getGroupFormationAlgorithm(project);
                    List<Group> groups = groupFormationAlgorithm.calculateGroups(project);
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
    }

    public Project getSurveyProjectName(GroupWorkContext projectContext) {
        if (!GroupWorkContextUtil.isGamingOrAutomatedGroupFormation(projectContext)) {
            return new Project(projectContext.toString());
        } else {
            String projectName = projectDAO.getActiveSurveyProject(projectContext);
            if (projectName == null) {
                // if result is empty create new project, add all the questions to it and return this
                Project project = new Project(surveyMapper.createNewProject(projectContext));
                projectDAO.setGroupFormationMechanism(GroupFormationMechanism.UserProfilStrategy, project);
                project.setGroupWorkContext(projectContext);
                return project;
            } else {
                Project project  = new Project(projectName);
                project.setGroupWorkContext(projectContext);
                return project;
            }
        }
    }

    public Boolean isStudentInProject(User user){
        List<Project> projects = iManagement.getProjectsStudent(user);
        return projects != null && !projects.isEmpty();
    }
}
