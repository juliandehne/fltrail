package unipotsdam.gf.modules.assessment.controller.view;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.QuizAnswer;
import unipotsdam.gf.modules.assessment.controller.model.*;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessmentDummy;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/assessments")
public class QuizView implements IPeerAssessment {
    private static IPeerAssessment peer =  new PeerAssessmentDummy();   //TestSubject
    //private static IPeerAssessment peer =  new PeerAssessment();      //correct DB-conn and stuff
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectId}/quiz/{quizId}")
    @Override
    public Quiz getQuiz(@PathParam("projectId") String projectId, @PathParam("quizId") String quizId) {
        return peer.getQuiz(projectId, quizId);
    }  ///////////////////////////////funktioniert wie geplant//////////////////////////////////

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectId}/quiz")
    @Override
    public ArrayList<Quiz> getQuiz(@PathParam("projectId") String projectId) {
        return peer.getQuiz(projectId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/peer/project/{projectId}/group/{groupId}")
    @Override
    public void postPeerRating(ArrayList<PeerRating> peerRatings,@PathParam("projectId") String projectId, @PathParam("groupId") String groupId){
        peer.postPeerRating(peerRatings, projectId,groupId);
        //todo: checkout the POST-variable. should be peerRating but its null atm.
    }

    @Override
    public void answerQuiz(StudentAndQuiz studentAndQuiz, QuizAnswer quizAnswer) {

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
    }  ///////////////////////////////funktioniert wie geplant//////////////////////////////////


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/quiz")
    @Override
    public void createQuiz(StudentAndQuiz studentAndQuiz) {
        peer.createQuiz(studentAndQuiz);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/calculate")
    @Override
    public List<Grading> calculateAssessment(ArrayList<Performance> totalPerformance) {
        return peer.calculateAssessment(totalPerformance);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mean/project/{projectId}")
    @Override
    public int meanOfAssessement(@PathParam("projectId") String ProjectId) {

        return peer.meanOfAssessement(ProjectId);
    }  ///////////////////////////////return 0//////////////////////////////////

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/total/project/{projectId}/student/{student}")
    public ArrayList<Performance> getTotalAssessment(@PathParam("projectId") String ProjectId,@PathParam("student") String student){
        StudentIdentifier studentIdentifier = new StudentIdentifier(ProjectId, student);
        return getTotalAssessment(studentIdentifier);
    }  ///////////////////////////////funktioniert wie geplant//////////////////////////////////

    @Override
    public ArrayList<Performance> getTotalAssessment(StudentIdentifier studentIdentifier) {
        return peer.getTotalAssessment(studentIdentifier);
    }  ///////////////////////////////funktioniert wie geplant//////////////////////////////////


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/dummy/totalperformance")
    public List<Performance> getTotalAssessment() {
        ArrayList<Performance> result = new ArrayList<>();
        StudentIdentifier student = new StudentIdentifier("projekt","student");
        int[] quiz = {1,0,1,1,1,0};
        Performance pf = new Performance(student,quiz,"toller dude",quiz);
        Performance pf2 = new Performance(student,quiz,"super",quiz);
        result.add(pf);
        result.add(pf2);
        return result;
    }  ///////////////////////////////returns what i expect it to return!!!!!//////////////////////////////////

}
