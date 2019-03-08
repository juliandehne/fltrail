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
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.group.preferences.survey.SurveyMapper;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.scheduler.Scheduler;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
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
    private IGroupFinding groupfinding;

    public void saveSurveyData(Project project, HashMap<String, String> data, HttpServletRequest req, GroupWorkContext groupWorkContext, ServletContextEvent sce)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
       /* if (groupWorkContext==GroupWorkContext.evaluation){
            surveyMapper.saveEvaluation(data,project.getName(),req);
        }else{*/
            surveyMapper.saveData(data, project.getName(), req);
            List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
            if (usersByProjectName.size() == GroupAlConfig.GROUPAL_SURVEY_COHORT_SIZE) {
                List<Group> groups = groupfinding.getGroupFormationAlgorithm(project).calculateGroups(project);
                groupfinding.persistGroups(groups, project);
                phases.endPhase(Phase.GroupFormation, project);

                for (User user : usersByProjectName) {
                    EMailMessage message = Messages.SurveyGroupFormation(project, user.getEmail());
                    emailService.sendSingleMessage(message, user);
                }


                //todo: sende Email an alle
                switch (groupWorkContext){
                    case dota:
                    case dota_test:
                    case dota_survey_a2:
                    case fl_test:
                    case fl_survey_a4:
                    case dota_survey_a1:
                    case fl_survey_a3:
                    case fl_lausberg:
                    case other_survey_a1:
                    case other_survey_a2:
                        Scheduler scheduler = new Scheduler(project, emailService);
                        scheduler.start(sce);
                        break;
                    case fl:
                    case evaluation:
                        break;
                }
            }
        //}
    }

    public Project getSurveyProjectName(String projectContext) {
        String projectName = projectDAO.getActiveSurveyProject(projectContext);
        if (projectName == null) {
            // if result is empty create new project, add all the questions to it and return this
            Project project = new Project(surveyMapper.createNewProject(GroupWorkContext.valueOf(projectContext)));
            projectDAO.setGroupFormationMechanism(GroupFormationMechanism.UserProfilStrategy, project);
            return project;
        } else {
            return new Project(projectName);
        }
    }
}
