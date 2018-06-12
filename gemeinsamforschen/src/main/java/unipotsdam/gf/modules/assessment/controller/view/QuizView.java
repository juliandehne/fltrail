package unipotsdam.gf.modules.assessment.controller.view;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.*;
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


    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student){
        return peer.getAssessmentDataFromDB(student);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectId}/student/{studentId}")
    public Assessment getAssessmentDataFromDB(@PathParam("projectId") String projectId,@PathParam("studentId") String studentId){
        StudentIdentifier student = new StudentIdentifier(projectId, studentId);
        return getAssessmentDataFromDB(student);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/quiz")
    @Override
    public void createQuiz(StudentAndQuiz studentAndQuiz) {
        peer.createQuiz(studentAndQuiz);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/calculate")
    @Override
    public Grades calculateAssessment(TotalPerformance totalPerformance) { //todo: maybe the return variable is the problem why it doesnt work.

        return peer.calculateAssessment(totalPerformance);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mean/project/{projectId}")
    @Override
    public int meanOfAssessement(@PathParam("projectId") String ProjectId) {
        return peer.meanOfAssessement(ProjectId);
    }
}
