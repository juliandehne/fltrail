package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.FullContribution;
import unipotsdam.gf.modules.assessment.controller.model.PeerRating;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.group.preferences.survey.SurveyData;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.PeerAssessmentProcess;
import unipotsdam.gf.session.GFContext;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/assessment")
public class AssessmentView {

    @Inject
    private PeerAssessmentProcess peerAssessmentProcess;

    @Inject
    private IPeerAssessment peer;      //correct DB-conn and stuff

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private GFContexts gfContexts;

    @POST
    @Path("/grading/start/projects/{projectName}")
    public void startGrading(@PathParam("projectName") String projectName) {
        peerAssessmentProcess.startGrading(new Project(projectName));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/peerRating/project/{projectName}")
    public void postPeerRating(ArrayList<PeerRating> peerRatings, @PathParam("projectName") String projectName)
            throws IOException {
        peer.postPeerRating(peerRatings, projectName);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/groupRate/project/{projectName}")
    public Integer whichGroupToRate(@PathParam("projectName") String projectName, @Context HttpServletRequest req)
            throws IOException {
        String userEmail = gfContexts.getUserEmail(req);
        User user = userDAO.getUserByEmail(userEmail);
        Project project = projectDAO.getProjectByName(projectName);
        return peer.whichGroupToRate(project, user);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("contributions/project/{projectName}")
    public List<FullContribution> getContributionsForProject(
            @Context HttpServletRequest req, @PathParam("projectName") String projectName) throws IOException {
        List<FullContribution> result;
        Project project = projectDAO.getProjectByName(projectName);
        String userEmail = gfContexts.getUserEmail(req);
        User user = userDAO.getUserByEmail(userEmail);
        Integer groupId = peer.whichGroupToRate(project, user);
        result = peer.getContributionsFromGroup(project, groupId);
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/contributionRating/project/{projectName}/group/{groupId}/fromPeer/{fromPeer}")
    public void postContributionRating(
            Map<FileRole, Integer> contributionRatings, @PathParam("groupId") String groupId,
            @PathParam("projectName") String projectName, @PathParam("fromPeer") String fromPeer) {
        peerAssessmentProcess.postContributionRating(contributionRatings, groupId, projectName, fromPeer);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/whatToRate/project/{projectName}/student/{userName}")
    public String whatToRate(
            @Context HttpServletRequest req, @PathParam("projectName") String projectName,
            @PathParam("userName") String userName) throws IOException {
        Project project = new Project(projectName);
        String userEmail = gfContexts.getUserEmail(req);
        User user = new User(userEmail);
        return peer.whatToRate(project, user);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/project/{projectName}")
    public Map<StudentIdentifier, Double> getAssessmentForProject(@PathParam("projectName") String projectName) {
        return peer.getAssessmentForProject(projectName);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/project/{projectName}/student/{studentEmail}")
    public Double getAssessmentForStudent(
            @PathParam("projectName") String projectName, @PathParam("studentEmail") String studentEmail) {
        StudentIdentifier student = new StudentIdentifier(projectName, studentEmail);
        return peer.getAssessmentForStudent(student);
    }

    ////////////////////////////////funktioniert///////////////////////////////////////////
    //todo: is unnecessary I guess. finalizing should just happen when phase ends
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/finalize/project/{projectName}")
    public String calculateAssessment(@PathParam("projectName") String projectName) {
        Project project = new Project(projectName);
        peer.finalizeAssessment(project);
        return "successfully finalized " + projectName;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mean/project/{projectName}")
    public int meanOfAssessment(@PathParam("projectName") String ProjectId) {
        return peer.meanOfAssessment(ProjectId);
    }  ///////////////////////////////return 0//////////////////////////////////

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/total/project/{projectName}/student/{student}")
    public ArrayList<Performance> getTotalAssessment(
            @PathParam("projectName") String ProjectId, @PathParam("student") String student) {
        StudentIdentifier userNameentifier = new StudentIdentifier(ProjectId, student);
        return getTotalAssessment(userNameentifier);
    }  //////////dummy/////////////funktioniert wie geplant//////////////////////////////////

    private ArrayList<Performance> getTotalAssessment(StudentIdentifier userNameentifier) {
        return peer.getTotalAssessment(userNameentifier);
    }  /////////dummy/////////////funktioniert wie geplant//////////////////////////////////

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
    public SurveyData getInternalAssessmentQuestions(@PathParam("projectId") String projectId) throws Exception {
        InternalAssessmentQuestions internalAssessmentQuestions = new InternalAssessmentQuestions();
        SurveyData questionsInSurveyJSFormat = internalAssessmentQuestions.getQuestionsInSurveyJSFormat();
        return questionsInSurveyJSFormat;
    }

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
    @Path("/save/projects/{projectName}/context/{context}/user/{userFeedbacked}")
    public void saveInternalAssessment(
            HashMap<String, String> data, @PathParam("projectName") String projectName,
            @PathParam("context") String context, @PathParam("userFeedbacked") String userFeedbacked, @Context
            HttpServletRequest req)
            throws Exception {
            Project project = new Project(projectName);
            User user = gfContexts.getUserFromSession(req);
            User feedbackedUser = new User(userFeedbacked);
            peerAssessmentProcess.persistInternalAssessment(project, user, feedbackedUser);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/nextGroupMemberToRate/projects/{projectName}")
    public User getNextGroupMemberToRateInternally(@PathParam("projectName") String projectName, @Context
            HttpServletRequest req) throws IOException {
        Project project = new Project(projectName);
        User user = gfContexts.getUserFromSession(req);
        return peerAssessmentProcess.getNextUserToRateInternally(project, user);
    }


}
