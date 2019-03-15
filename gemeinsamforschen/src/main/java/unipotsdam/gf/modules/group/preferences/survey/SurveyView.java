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
    private IGroupFinding groupfinding;


    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private SurveyMapper surveyMapper;

    @Inject
    private UserDAO userDAO;

    @Inject
    private GroupFormationProcess groupFormationProcess;

    private ServletContextEvent sce;

    /**
     * // get project where name like projectContext and is active
     *
     * @param projectContext
     * @return
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/project/name/{projectContext}")
    public Project getProjectName(@PathParam("projectContext") String projectContext) {
        return surveyProcess.getSurveyProjectName(GroupWorkContext.valueOf(projectContext));
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

 /*   @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/evaluation/project/{projectId}")
    public SurveyData getEvaluationQuestions(@PathParam("projectId") String projectId) throws Exception {
        Project project = projectDAO.getProjectByName(projectId);
        GroupWorkContext groupWorkContext = GroupWorkContext.evaluation;
        return surveyMapper.getItemsFromDB(groupWorkContext, project);
    }*/

    /**
     * get all current survey projects
     *
     * @return
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/projects")
    public java.util.List<Project> getCurrentSurveyProjects() {
        List<Project> surveyProjects = projectDAO.getSurveyProjects();
        return surveyProjects;

    }

    /*    *//**
     * get the status of a project
     * @return
     *//*
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/status")
    public ProjectStatus getProjectStatus() {

        // TODO implement
        return new ProjectStatus();
    }*/

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
        if (GroupWorkContextUtil.isSurveyContext(groupWorkContext)) {
            if (GroupWorkContextUtil.isGamingOrAutomatedGroupFormation(groupWorkContext)) {
                project = surveyProcess.getSurveyProjectName(groupWorkContext);
            }
            surveyProcess.saveSurveyData(project, data, req, groupWorkContext, sce);
        } else {
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
            @Context HttpServletRequest req) {
        User user = new User(userEmail);
        Project projectName = getProjectName(context);
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
     * participant count
     *
     * @param projectName
     * @param req
     * @return
     */
    @GET
    @Path("/participantCount/project/{projectName}")
    public String getParticipantCount(
            @PathParam("projectName") String projectName, @Context HttpServletRequest req) {
        ParticipantsCount participantsCount = projectDAO.getParticipantCount(new Project(projectName));
        return Integer.toString(participantsCount.getParticipants());
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
        Project project = new Project(projectName);
        // todo set GroupWorkContext
        groupfinding.deleteGroups(project);
        List<Group> groups = groupfinding.getGroupFormationAlgorithm(project).calculateGroups(project);
        groupfinding.persistGroups(groups, project);
        return groups;
    }
}