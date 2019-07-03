package unipotsdam.gf.modules.assessment;

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
import java.io.IOException;
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
            @PathParam("groupId") String groupId) {
        List<FullContribution> result;
        Project project = projectDAO.getProjectByName(projectName);
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
        return internalAssessmentQuestions.getQuestionsInSurveyJSFormat();
    }

    /**
     * save the answers a user has given in a survey
     *
     * @param data keys are groupWork skills and values are string encoded integers from 1 to 5
     * @param projectName of interest
     * @param req context to get userCredentials
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
    @Produces(MediaType.APPLICATION_JSON)
    public UserAssessmentDataHolder getGradesForProject(@PathParam("projectName") String projectName) {
        // dummy implementierung
        /*PodamFactoryImpl podamFactory = new PodamFactoryImpl();
        ArrayList<UserPeerAssessmentData> result = new ArrayList<UserPeerAssessmentData>();
        for (int i=0;i<10;i++) {
            UserPeerAssessmentData elem = podamFactory.manufacturePojo(UserPeerAssessmentData.class);
            result.add(elem);
        }
        UserAssessmentDataHolder userAssessmentDataHolder = new UserAssessmentDataHolder(result);
*/
        // comment this in if you feel ready for it
        UserAssessmentDataHolder userAssessmentDataHolder = new UserAssessmentDataHolder();
        userAssessmentDataHolder.setData(peer.getUserAssessmentsFromDB(new Project(projectName)));

        return userAssessmentDataHolder;
    }

    @POST
    @Path("/grades/project/{projectName}/sendData")
    public void sendData(@PathParam("projectName") String projectName, UserAssessmentDataHolder userAssessmentDataHolder) {
         peerAssessmentProcess.saveGrades(new Project(projectName), userAssessmentDataHolder);
    }

}
