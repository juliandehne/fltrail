package unipotsdam.gf.modules.assessment;

import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.FullContribution;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.preferences.survey.SurveyData;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.PeerAssessmentProcess;
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
    @Path("/contributions/project/{projectName}/groupId/{groupId}")
    public List<FullContribution> getContributionsForProject(
            @Context HttpServletRequest req, @PathParam("projectName") String projectName,
            @PathParam("groupId") String groupId) throws IOException {
        List<FullContribution> result;
        Project project = projectDAO.getProjectByName(projectName);
        String userEmail = gfContexts.getUserEmail(req);
        User user = userDAO.getUserByEmail(userEmail);
        //Integer groupId = peer.whichGroupToRate(project, user);
        int groupIdParsed = Integer.parseInt(groupId);
        result = peer.getContributionsFromGroup(project, groupIdParsed);
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/contributionRating/project/{projectName}/group/{groupId}/fromPeer/{fromPeer}")
    public void postContributionRating(
            Map<FileRole, Integer> contributionRatings, @PathParam("groupId") String groupId,
            @PathParam("projectName") String projectName, @PathParam("fromPeer") String fromPeer) {
        Boolean isStudent = userDAO.getUserByEmail(fromPeer).getStudent();
        peerAssessmentProcess
                .postContributionRating(contributionRatings, groupId, new Project(projectName), fromPeer, isStudent);
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


    /**
     * get the survey questions
     *
     * @param projectId Id of project you are looking for
     * @return questions to rate group members in JSON format
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/data/project/{projectId}")
    public SurveyData getInternalAssessmentQuestions(@PathParam("projectId") String projectId) {
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
            @PathParam("context") String context, @PathParam("userFeedbacked") String userFeedbacked,
            @Context HttpServletRequest req) throws Exception {
        Project project = new Project(projectName);
        User user = gfContexts.getUserFromSession(req);
        User feedbackedUser = new User(userFeedbacked);
        peerAssessmentProcess.persistInternalAssessment(project, user, feedbackedUser, data);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/nextGroupMemberToRate/projects/{projectName}")
    public User getNextGroupMemberToRateInternally(
            @PathParam("projectName") String projectName, @Context HttpServletRequest req) throws IOException {
        Project project = new Project(projectName);
        User user = gfContexts.getUserFromSession(req);
        return peerAssessmentProcess.getNextUserToRateInternally(project, user);
    }


    @POST
    @Path("/grading/start/projects/{projectName}")
    public void startGrading(@PathParam("projectName") String projectName) {
        peerAssessmentProcess.startGrading(new Project(projectName));
    }

    @GET
    @Path("/grades/project/{projectName}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public UserAssessmentDataHolder getGradesForProject(@PathParam("projectName") String projectName) {
        // dummy implementierung
        PodamFactoryImpl podamFactory = new PodamFactoryImpl();
        ArrayList<UserPeerAssessmentData> result = new ArrayList<UserPeerAssessmentData>();
        for (int i=0;i<10;i++) {
            UserPeerAssessmentData elem = podamFactory.manufacturePojo(UserPeerAssessmentData.class);
            result.add(elem);
        }
        UserAssessmentDataHolder userAssessmentDataHolder = new UserAssessmentDataHolder(result);

        // comment this in if you feel ready for it
        //userAssessmentDataHolder.setData(peer.getUserAssessmentsFromDB(new Project(projectName)));

        return userAssessmentDataHolder;
    }

    @POST
    @Path("/grades/project/{projectName}/sendData")
    public void sendData(@PathParam("projectName") String projectName, UserAssessmentDataHolder userAssessmentDataHolder) {
         peerAssessmentProcess.saveGrades(new Project(projectName), userAssessmentDataHolder);
    }

}
