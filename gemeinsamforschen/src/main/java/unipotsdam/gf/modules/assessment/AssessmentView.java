package unipotsdam.gf.modules.assessment;

import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.Contribution;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.GroupDAO;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Boolean;

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

    @Inject
    private GroupDAO groupDAO;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/groupRate/project/{projectName}")
    public Integer whichGroupToRate(@PathParam("projectName") String projectName, @Context HttpServletRequest req)
            throws Exception {
        String userEmail = gfContexts.getUserEmail(req);
        User user = userDAO.getUserByEmail(userEmail);
        Project project = projectDAO.getProjectByName(projectName);
        Integer groupId = groupDAO.getMyGroupId(user, project);
        return peer.whichGroupToRate(project, groupId);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/contributions/project/{projectName}/groupId/{groupId}")
    public List<Contribution> getContributionsForProject(
            @Context HttpServletRequest req, @PathParam("projectName") String projectName,
            @PathParam("groupId") String groupId) throws Exception {
        List<Contribution> result;
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
            @PathParam("projectName") String projectName, @PathParam("fromPeer") String fromPeer) throws Exception {
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
     * @param data        keys are groupWork skills and values are string encoded integers from 1 to 5
     * @param projectName of interest
     * @param req         context to get userCredentials
     */
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/save/projects/{projectName}/context/{context}/user/{userFeedbacked}")
    public void saveInternalAssessment(
            HashMap<String, Integer> data, @PathParam("projectName") String projectName,
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


    /**
     * starts studentAssessment
     *
     * @param projectName of interest
     * @throws Exception needs user not null to work properly
     */
    @POST
    @Path("/studentAssessment/start/projects/{projectName}")
    public void startStudentAssessment(@PathParam("projectName") String projectName) throws Exception {
        peerAssessmentProcess.startStudentAssessments(new Project(projectName));
    }

    @GET
    @Path("/grades/project/{projectName}")
    @Produces({MediaType.APPLICATION_JSON})
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
    public void sendData(
            @PathParam("projectName") String projectName, UserAssessmentDataHolder userAssessmentDataHolder)
            throws Exception {
        peerAssessmentProcess.saveGrades(new Project(projectName), userAssessmentDataHolder);
    }


    @GET
    @Path("/grades/project/{projectName}/excel")
    @Produces("application/vnd.ms-excel")
    public Response downloadExcelFile(@PathParam("projectName") String projectName) throws IOException, WriteException {

        List<UserPeerAssessmentData> userAssessmentsFromDB = peer.getUserAssessmentsFromDB(new Project(projectName));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableWorkbook workbook = Workbook.createWorkbook(baos);

        WritableSheet grades = workbook.createSheet("Grades", 1);

        Label label = new Label(0, 0, "Name");
        grades.addCell(label);

        Label label1 = new Label(1, 0, "Email");
        grades.addCell(label1);

        Label label2 = new Label(2, 0, "Gruppe");
        grades.addCell(label2);

        Label label3 = new Label(3, 0, "Gruppenarbeit Peer Bewertung");
        grades.addCell(label3);

        Label label4 = new Label(4, 0, "Gruppenarbeit Produkte");
        grades.addCell(label4);

        Label label5 = new Label(5, 0, "Vorgeschlagenes Note");
        grades.addCell(label5);

        Label label6 = new Label(6, 0, "Finalisierte Note");
        grades.addCell(label6);

        Label label7 = new Label(7, 0, "Produktnote der dozierenden Person");
        grades.addCell(label7);

        int rowCount = 1;

        for (UserPeerAssessmentData userPeerAssessmentData : userAssessmentsFromDB) {
            String name = userPeerAssessmentData.getUser().getName();
            if (name != null) {
                Label nameCell = new Label(0, rowCount, name);
                grades.addCell(nameCell);
            }

            String email = userPeerAssessmentData.getUser().getEmail();
            if (email != null) {
                Label emailCell = new Label(1, rowCount, email);
                grades.addCell(emailCell);
            }

            Integer groupId = userPeerAssessmentData.getGroupId();
            if (groupId != null) {
                Number groupIdCell = new Number(2, rowCount, groupId);
                grades.addCell(groupIdCell);
            }

            Double groupWorkRating = userPeerAssessmentData.getGroupWorkRating();
            if (groupWorkRating != null) {
                Number groupWorkRatingIdCell = new Number(3, rowCount, groupWorkRating);
                grades.addCell(groupWorkRatingIdCell);
            }

            Double groupProductRating = userPeerAssessmentData.getGroupProductRating();
            if (groupProductRating != null) {
                Number groupProductRatingCell = new Number(4, rowCount, groupProductRating);
                grades.addCell(groupProductRatingCell);
            }

            Double suggestedRating = userPeerAssessmentData.getSuggestedRating();
            if (suggestedRating != null ) {
                Number suggestedRatingCell = new Number(5, rowCount, suggestedRating);
                grades.addCell(suggestedRatingCell);
            }

            Double finalRating = userPeerAssessmentData.getFinalRating();
            if (finalRating != null) {
                Number finalRatingCell = new Number(6, rowCount, finalRating);
                grades.addCell(finalRatingCell);
            }

            final Double docentProductRating = userPeerAssessmentData.getDocentProductRating();
            Number docentProductRatingCell = new Number(7, rowCount, docentProductRating);
            grades.addCell(docentProductRatingCell);

            rowCount++;
        }

        workbook.write();
        workbook.close();

        StreamingOutput streamingOutput = output -> {
            output.write(baos.toByteArray());
            output.flush();
            output.close();
        };

        Response.ResponseBuilder responseBuilder = Response.ok(streamingOutput);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"grades.xls\"");
        return responseBuilder.build();

    }


}
