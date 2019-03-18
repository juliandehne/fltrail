package unipotsdam.gf.modules.group.preferences.survey;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.GroupFormationProcess;
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
import java.io.IOException;
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



    /**
     * // get project where name like projectContext and is active
     *
     * @param projectContext
     * @return
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/project/name/{projectContext}/email/{email}")
    public Project getProjectName(@PathParam("projectContext") String projectContext, @PathParam("email") String email) throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        String emailString = email;
        if (email.trim().equals("UNKNOWN")) {
            emailString = null;
        }
        return surveyProcess.getSurveyProjectNameOrInitialize(GroupWorkContext.valueOf(projectContext),emailString);
    }


    /**
     * get the survey questions
     *
     * @param projectId
     * @return
     * @throws Exception
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/data/project/{projectId}")
    public SurveyData getSurveyData(@PathParam("projectId") String projectId) throws Exception {
        Project project = projectDAO.getProjectByName(projectId);
        GroupWorkContext groupWorkContext = surveyMapper.getGroupWorkContext(project);
        return surveyMapper.getItemsFromDB(groupWorkContext, project);
    }


    /**
     * get all current survey projects
     *
     * @return

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/projects/context/{context}")
    public Project getCurrentSurveyProjects(@PathParam("context") String context) {
        GroupWorkContext groupWorkContext = GroupWorkContext.valueOf(context);
        Project surveyProjects = projectDAO.getSurveyProjects(Phase.GroupFormation, groupWorkContext);
        return surveyProjects;
    }
     */


    /**
     * save the answers a user has given in a survey
     *
     * @param data
     * @param projectName
     * @param req
     * @throws RocketChatDownException
     * @throws UserDoesNotExistInRocketChatException
     * @throws WrongNumberOfParticipantsException
     * @throws JAXBException
     * @throws IOException
     */
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/save/projects/{projectName}/context/{context}")
    public void saveSurvey(
            HashMap<String, String> data, @PathParam("projectName") String projectName,
            @PathParam("context") String context, @Context HttpServletRequest req) throws Exception {
        GroupWorkContext groupWorkContext = GroupWorkContext.valueOf(context);
        Project project = new Project(projectName);
        project.setGroupWorkContext(groupWorkContext);
        // check if it is surveyContext
        surveyProcess.saveSurveyData(project, data, req, groupWorkContext);
    }


    /**
     * checks if there is a user session
     *
     * @param req
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user")
    public Boolean loggedIn(@Context HttpServletRequest req) {
        try {
            String userSessionEmail = gfContexts.getUserEmail(req);
            if (userSessionEmail == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * checks if the user email exists
     *
     * @param userEmail
     * @param req
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{userEmail}/context/{context}")
    public Boolean authenticate(
            @PathParam("userEmail") String userEmail, @PathParam("context") String context,
            @Context HttpServletRequest req) throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        User user = new User(userEmail);
        Project projectName = getProjectName(context, userEmail);
        List<User> usersByProjectName = userDAO.getUsersByProjectName(projectName.getName());
        if (usersByProjectName.contains(user)) {
            gfContexts.updateUserWithEmail(req, userDAO.getUserByEmail(userEmail));
            return true;
        }
        return false;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/checkDoubleParticipation/user/{userEmail}")
    public String checkDoubleParticipation(@PathParam("userEmail") String userEmail, @Context HttpServletRequest req) {
        User user = new User(userEmail);
        return surveyProcess.isStudentInProject(user).toString();
    }



    /**
     * participant needed count
     *
     * @param projectName
     * @param req
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/participantCountNeeded/project/{projectName}/context/{context}")
    public ParticipantsCount getParticipantNeededCount(
            @PathParam("projectName") String projectName, @PathParam("context") String context,
            @Context HttpServletRequest req) {

        int needed = GroupWorkContextUtil.getParticipantNeeded(GroupWorkContext.valueOf(context));
        ParticipantsCount participantsCount = projectDAO.getParticipantCount(new Project(projectName));

        if (needed >= 0) {
            participantsCount.setParticipantsNeeded(needed);
        }
        return participantsCount;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/projects/{projectName}/buildGroups")
    public List<Group> buildGroups(@PathParam("projectName") String projectName)
            throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        SurveyProject project = new SurveyProject(projectName, null);
        return surveyProcess.formGroupsForSurvey(project);
    }
}