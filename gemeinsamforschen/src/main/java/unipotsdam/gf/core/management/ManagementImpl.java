package unipotsdam.gf.core.management;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.group.GroupDAO;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.project.ProjectConfiguration;
import unipotsdam.gf.core.management.project.ProjectConfigurationDAO;
import unipotsdam.gf.core.management.project.ProjectDAO;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.management.user.UserInterests;
import unipotsdam.gf.core.management.user.UserProfile;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;

/**
 * Created by dehne on 31.05.2018.
 */
@ManagedBean
@Resource
@Singleton
public class ManagementImpl implements Management {

    private UserDAO userDAO;
    private GroupDAO groupDAO;
    private ProjectDAO projectDAO;

    private MysqlConnect connect;

    @Inject
    public ManagementImpl(UserDAO userDAO, GroupDAO groupDAO, ProjectDAO projectDAO, MysqlConnect connect) {
        this.userDAO = userDAO;
        this.groupDAO = groupDAO;
        this.projectDAO = projectDAO;
        this.connect = connect;
    }

    @Override
    public void register(User user, Project project, UserInterests interests) {
        connect.connect();
        String mysqlRequest = "INSERT INTO projectuser (`projectId`, `userId`) values (?,?)";
        connect.issueInsertOrDeleteStatement(mysqlRequest, project.getId(), user.getId());
        connect.close();
    }

    /**
     * TODO @Axel bitte in modules/asessment verschieben
     *
     * @param projectId
     * @param quizId
     * @return
     */
    @Override
    public Quiz getQuizByProjectGroupId(String projectId, String quizId) {
        connect.connect();
        String mysqlRequest = "SELECT * FROM quiz where projectId=" + projectId + " , question=" + quizId;
        VereinfachtesResultSet vereinfachtesResultSet =
                connect.issueSelectStatement(mysqlRequest, "");
        boolean next = vereinfachtesResultSet.next();
        String question = "";
        ArrayList<String> correctAnswers = new ArrayList<String>();
        ArrayList<String> incorrectAnswers = new ArrayList<String>();
        String answer = "";
        Boolean correct = false;
        String mcType = "";
        while (next) {
            mcType = vereinfachtesResultSet.getString("mcType");
            question = vereinfachtesResultSet.getString("question");
            answer = vereinfachtesResultSet.getString("answer");
            correct = vereinfachtesResultSet.getBoolean("correct");
            if (correct) {
                correctAnswers.add(answer);
            } else {
                incorrectAnswers.add(answer);
            }
            next = vereinfachtesResultSet.next();
        }
        Quiz quiz = new Quiz(mcType, question, correctAnswers, incorrectAnswers);
        connect.close();
        return quiz;
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public void create(User user, UserProfile profile) {
        userDAO.persist(user, profile);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public Boolean exists(User user) {
        return userDAO.exists(user);
    }

    @Override
    public void create(Project project) {
        projectDAO.persist(project);
    }

    @Override
    public void delete(Project project) {
        projectDAO.delete(project);
    }

    @Override
    public Boolean exists(Project project) {
        return projectDAO.exists(project);
    }

    @Override
    public void create(Group group) {
        groupDAO.persist(group);
    }

    @Override
    public void update(Group group) {
        groupDAO.update(group);
    }

    @Override
    public Boolean exists(Group group) {
        return groupDAO.exists(group);
    }

    @Override
    public void create(ProjectConfiguration projectConfiguration, Project project) {
        ProjectConfigurationDAO projectConfigurationDAO = new ProjectConfigurationDAO();
        projectConfigurationDAO.persistProjectConfiguration(projectConfiguration, project);
    }

    @Override
    public ProjectConfiguration getProjectConfiguration(Project project) {
        ProjectConfigurationDAO projectConfigurationDAO = new ProjectConfigurationDAO();
        return projectConfigurationDAO.loadProjectConfiguration(project);
    }
}
