package unipotsdam.gf.modules.assessment.controller.view;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.Assessment;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessmentDummy;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/assessments")
public class QuizView implements IPeerAssessment {
    private static IPeerAssessment peer =  new PeerAssessmentDummy();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectId}/quiz/{quizId}")
    @Override
    public Quiz getQuiz(@PathParam("projectId") String projectId, @PathParam("quizId") String quizId) {
        return peer.getQuiz(projectId, quizId);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/assessment")
    @Override
    public void addAssessmentDataToDB(Assessment assessment) {
        peer.addAssessmentDataToDB(assessment);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/project/{projectId}/student/{studentId}")
    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student){//@PathParam("projectId") String projectId,@PathParam("studentId") String studentId){
    //StudentIdentifier student = new StudentIdentifier(projectId, studentId);
        return peer.getAssessmentDataFromDB(student);
    }

    @Override
    public void createQuiz(StudentIdentifier student, Quiz quiz) {

    }

    @Override
    public int[] calculateAssessment(Performance[] performanceOfAllStudents) {
        return new int[0];
    }

    @Override
    public int meanOfAssessement(String ProjectId) {
        return 0;
    }
}
