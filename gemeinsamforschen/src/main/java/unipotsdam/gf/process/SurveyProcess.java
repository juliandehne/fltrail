package unipotsdam.gf.process;

import unipotsdam.gf.config.GroupAlConfig;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.group.preferences.survey.SurveyMapper;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;

import javax.inject.Inject;
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

    public void saveSurveyData(Project project, HashMap<String, String> data) throws RocketChatDownException,
            UserDoesNotExistInRocketChatException {

        surveyMapper.saveData(data, project.getName());

        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        if (usersByProjectName.size() == GroupAlConfig.GROUPAL_SURVEY_COHORT_SIZE) {
            phases.endPhase(Phase.GroupFormation, project);
        }
    }

    public Project getSurveyProjectName(String projectContext) {
        String projectName = projectDAO.getActiveSurveyProject(projectContext);
        if (projectName == null) {
            // if result is empty create new project, add all the questions to it and return this
            return new Project(surveyMapper.createNewProject(GroupWorkContext.valueOf(projectContext)));
        } else {
            return new Project(projectName);
        }
    }
}
