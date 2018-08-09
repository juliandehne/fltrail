package unipotsdam.gf.modules.assessment.controller.view;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.QuizAnswer;
import unipotsdam.gf.modules.assessment.controller.model.*;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/assessments")
public class QuizView implements IPeerAssessment {
    //private static IPeerAssessment peer =  new PeerAssessmentDummy();   //TestSubject
    private static IPeerAssessment peer =  new PeerAssessment();      //correct DB-conn and stuff
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectId}/quiz/{quizId}/author/{author}")
    @Override
    public Quiz getQuiz(@PathParam("projectId") String projectId, @PathParam("quizId") String quizId, @PathParam("author") String author) {
        try{
            String question=java.net.URLDecoder.decode(quizId,"UTF-8");
            return peer.getQuiz(projectId, question, author);
        }catch(UnsupportedEncodingException e){
            throw new AssertionError("UTF-8 is unknown");
        }
    }  ///////////////////////////////funktioniert//////////////////////////////////

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectId}/quiz")
    @Override
    public ArrayList<Quiz> getQuiz(@PathParam("projectId") String projectId) {
        return peer.getQuiz(projectId);
    }
    //////////////////////////////////////////funktioniert///////////////////////////////////////

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/quiz/{quizId}")
    @Override
    public void deleteQuiz(@PathParam("quizId") String quizId) {
        try {
            String question = java.net.URLDecoder.decode(quizId, "UTF-8");
            peer.deleteQuiz(question);
        }catch(UnsupportedEncodingException e){
            throw new AssertionError("UTF-8 is unknown");
        }
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
    @Path("/get/project/{projectId}/student/{studentId}")
    public Assessment getAssessmentDataFromDB(@PathParam("projectId") String projectId,@PathParam("studentId") String studentId){
        StudentIdentifier student = new StudentIdentifier(projectId, studentId);
        return getAssessmentDataFromDB(student);
    }  //////////dummy//////////////funktioniert wie geplant//////////////////////////////////


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/quiz")
    @Override
    public void createQuiz(StudentAndQuiz studentAndQuiz) {
        peer.createQuiz(studentAndQuiz);
    }
    ////////////////////////////////funktioniert///////////////////////////////////////////

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/calculate")
    @Override
    public Map<StudentIdentifier, Double> calculateAssessment(ArrayList<Performance> totalPerformance) {
        return peer.calculateAssessment(totalPerformance);
    }
    ////////////////////////funktioniert primitiv/////////todo: nicht als jersey zu nutzen///////////////////////////////

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/calculate/projectId/{projectId}/cheatChecker/{method}")
    public Map<String, Double> calculateAssessment(@PathParam("projectId") String projectId, @PathParam("method") String method) {
        return peer.calculateAssessment(projectId, method);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mean/project/{projectId}")
    @Override
    public int meanOfAssessment(@PathParam("projectId") String ProjectId) {
        return peer.meanOfAssessment(ProjectId);
    }  ///////////////////////////////return 0//////////////////////////////////

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/total/project/{projectId}/student/{student}")
    public ArrayList<Performance> getTotalAssessment(@PathParam("projectId") String ProjectId,@PathParam("student") String student){
        StudentIdentifier studentIdentifier = new StudentIdentifier(ProjectId, student);
        return getTotalAssessment(studentIdentifier);
    }  //////////dummy/////////////funktioniert wie geplant//////////////////////////////////

    @Override
    public ArrayList<Performance> getTotalAssessment(StudentIdentifier studentIdentifier) {
        return peer.getTotalAssessment(studentIdentifier);
    }  /////////dummy/////////////funktioniert wie geplant//////////////////////////////////



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/dummy/totalperformance")
    public List<Performance> getTotalAssessment() {
        List<Performance> result = new ArrayList<>();
        StudentIdentifier student = new StudentIdentifier("projekt","student");
        int[] quiz = {1,0,1,1,1,0};
        Map work = new HashMap<String, Integer>();
        work.put("responsibility", 1);
        work.put("partOfWork", 1);
        work.put("cooperation", 1);
        work.put("communication", 1);
        work.put("autonomous", 1);
        Map work2 = new HashMap<String, Integer>();
        work2.put("responsibility", 3);
        work2.put("partOfWork", 4);
        work2.put("cooperation", 5);
        work2.put("communication", 3);
        work2.put("autonomous", 4);
        Performance pf = new Performance();
        pf.setFeedback("ein toller typ");
        pf.setQuizAnswer(quiz);
        pf.setStudentIdentifier(student);
        pf.setWorkRating(work);
        Performance pf2 = new Performance();
        pf2.setFeedback("feini feini");
        pf2.setQuizAnswer(quiz);
        pf2.setStudentIdentifier(student);
        pf2.setWorkRating(work2);
        result.add(pf);
        result.add(pf2);
        return result;
    }  /////////dummy////////////returns what i expect it to return!!!!!//////////////////////////////////

}
