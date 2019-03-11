package unipotsdam.gf.modules.group.preferences.survey;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.SurveyProcess;
import unipotsdam.gf.process.tasks.ParticipantsCount;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.List;

@Path("/survey")
public class SurveyView {

    private final static Logger log = LoggerFactory.getLogger(SurveyView.class);

    @Inject
    private GFContexts gfContexts;

    @Inject
    private SurveyProcess surveyProcess;


    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private SurveyMapper surveyMapper;

    @Inject
    private UserDAO userDAO;

    private ServletContextEvent sce;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/project/name/{projectContext}")
    public Project getProjectName(@PathParam("projectContext") String projectContext) {
        // get project where name like projectContext and is active
        return surveyProcess.getSurveyProjectName(projectContext);
    }


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/data/project/{projectId}")
    public SurveyData getSurveyData(@PathParam("projectId") String projectId) throws Exception {
        Project project = projectDAO.getProjectByName(projectId);
        GroupWorkContext groupWorkContext = surveyMapper.getGroupWorkContext(project);
        return surveyMapper.getItemsFromDB(groupWorkContext, project);
    }

 /*   @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/evaluation/project/{projectId}")
    public SurveyData getEvaluationQuestions(@PathParam("projectId") String projectId) throws Exception {
        Project project = projectDAO.getProjectByName(projectId);
        GroupWorkContext groupWorkContext = GroupWorkContext.evaluation;
        return surveyMapper.getItemsFromDB(groupWorkContext, project);
    }*/

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/projects")
    public java.util.List<Project> getCurrentSurveyProjects() {
        List<Project> surveyProjects = projectDAO.getSurveyProjects();
        return surveyProjects;

    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/status")
    public ProjectStatus getProjectStatus() {

        // TODO implement
        return new ProjectStatus();
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/save/projects/{projectName}")
    public void saveSurvey(
            HashMap<String, String> data, @PathParam("projectName") String projectName,
            @Context HttpServletRequest req)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        GroupWorkContext groupWorkContext = surveyMapper.getGroupWorkContext(new Project(projectName));
        if (GroupWorkContextUtil.isSurveyContext(groupWorkContext)) {
            Project project = new Project(projectName);
            project.setGroupWorkContext(groupWorkContext);
            surveyProcess.saveSurveyData(project, data, req, groupWorkContext, sce);
        } else {
            Project project = new Project(projectName);
            project.setGroupWorkContext(groupWorkContext);
            surveyMapper.saveData(data, project, req);
        }
    }

/*    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/save/evaluation/projects/{projectName}")
    public void saveEvaluation(
            HashMap<String, String> data, @PathParam("projectName") String projectName,
            @Context HttpServletRequest req)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        GroupWorkContext groupWorkContext = GroupWorkContext.evaluation;
        surveyProcess.saveSurveyData(new Project(projectName), data, req, groupWorkContext);
    }*/

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/user")
    public String loggedIn(@Context HttpServletRequest req) {
        try {
            String userSessionEmail = gfContexts.getUserEmail(req);
            if (userSessionEmail == null)
                return "authenticated";
            else
                return "userEmail set";
        } catch (Exception e) {
            return "userEmail not set";
        }
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/user/{userEmail}")
    public String authenticate(@PathParam("userEmail") String userEmail, @Context HttpServletRequest req) {
        gfContexts.updateUserWithEmail(req, userDAO.getUserByEmail(userEmail));
        return "userEmail set";
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/checkDoubleParticipation/user/{userEmail}")
    public String checkDoubleParticipation(@PathParam("userEmail") String userEmail, @PathParam("context") String context, @Context HttpServletRequest req) {
        User user = new User(userEmail);
        return surveyProcess.isStudentInProject(user).toString();
    }

    @GET
    @Path("/participantCount/project/{projectName}")
    public String getParticipantCount(@PathParam("projectName") String projectName,
                                      @Context HttpServletRequest req) {
        ParticipantsCount participantsCount = projectDAO.getParticipantCount(new Project(projectName));
        return Integer.toString(participantsCount.getParticipants());
    }
}