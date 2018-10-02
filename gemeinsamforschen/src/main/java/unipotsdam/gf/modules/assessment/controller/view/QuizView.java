package unipotsdam.gf.modules.assessment.controller.view;

import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.PeerRating;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.Quiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentAndQuiz;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessment;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/assessments")
public class QuizView {
    private static IPeerAssessment peer = new PeerAssessment();      //correct DB-conn and stuff

    @Inject
    Management management;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectName}/quiz/{quizId}/author/{author}")
    public Quiz getQuiz(@PathParam("projectName") String projectName, @PathParam("quizId") String quizId, @PathParam("author") String author) {
        try {
            String question = java.net.URLDecoder.decode(quizId, "UTF-8");
            return peer.getQuiz(projectName, question, author);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 is unknown");
        }
    }  ///////////////////////////////funktioniert//////////////////////////////////

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectName}/quiz/author/{author}")
    public ArrayList<Quiz> getQuiz(@PathParam("projectName") String projectName, @PathParam("author") String author) {
        return peer.getQuiz(projectName, author);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectName}/quiz")
    public ArrayList<Quiz> getQuiz(@PathParam("projectName") String projectName) {
        return peer.getQuiz(projectName);
    }
    //////////////////////////////////////////funktioniert///////////////////////////////////////


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/peerRating/project/{projectName}")
    public void postPeerRating(ArrayList<PeerRating> peerRatings, @PathParam("projectName") String projectName) throws IOException {
        peer.postPeerRating(peerRatings, projectName);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/groupRate/project/{projectName}/student/{userName}")
    public Integer whichGroupToRate(@PathParam("projectName") String projectName, @PathParam("userName") String userName) {
        StudentIdentifier student = new StudentIdentifier(projectName, userName);
        return peer.whichGroupToRate(student);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/contributionRating/group/{groupId}/fromPeer/{fromPeer}")
    public void postContributionRating(Map<String, Integer> contributionRatings,
                                       @PathParam("groupId") String groupId,
                                       @PathParam("fromPeer") String fromPeer) throws IOException {
        peer.postContributionRating(groupId, fromPeer, contributionRatings);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/quizAnswer/projectName/{projectName}/userName/{userName}/")
    public void answerQuiz(Map<String, List<String>> questions, @PathParam("projectName") String projectName, @PathParam("userName") String userName) {
        StudentIdentifier student = new StudentIdentifier(projectName, userName);
        peer.answerQuiz(questions, student);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/quiz/{quizId}")
    public void deleteQuiz(@PathParam("quizId") String quizId) {
        try {
            String question = java.net.URLDecoder.decode(quizId, "UTF-8");
            peer.deleteQuiz(question);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 is unknown");
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/whatToRate/project/{projectName}/student/{userName}")
    public String whatToRate(@PathParam("projectName") String projectName, @PathParam("userName") String userName) {
        StudentIdentifier student = new StudentIdentifier(projectName, userName);
        return peer.whatToRate(student);
    }

    /*@POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/assessment")
    public void addAssessmentDataToDB(Assessment assessment) {
        peer.addAssessmentDataToDB(assessment);
    }*/

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/project/{projectName}")
    public Map<StudentIdentifier, Double> getAssessmentForProject(@PathParam("projectName") String projectName) {
        return peer.getAssessmentForProject(projectName);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/project/{projectName}/student/{studentEmail}")
    public Double getAssessmentForStudent(@PathParam("projectName") String projectName, @PathParam("studentEmail") String studentEmail) {
        StudentIdentifier student = new StudentIdentifier(projectName, studentEmail);
        return peer.getAssessmentForStudent(student);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @Path("/quiz")
    public String createQuiz(StudentAndQuiz studentAndQuiz) {

        Project project = management.getProjectByName(studentAndQuiz.getStudentIdentifier().getProjectName());
        User user = management.getUserByEmail(studentAndQuiz.getStudentIdentifier().getUserEmail());
        Boolean isStudent = user.getStudent();
        peer.createQuiz(studentAndQuiz);
        if (isStudent) {
            return "student";
        } else {
            return "docent";
        }
    }
    ////////////////////////////////funktioniert///////////////////////////////////////////
//todo: is unnecessary I guess. finalizing should just happen when phase ends
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/finalize/project/{projectName}")
    public String calculateAssessment(@PathParam("projectName") String projectName) {
        peer.finalizeAssessment(projectName);
        return "successfully finalized "+projectName;
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
    public ArrayList<Performance> getTotalAssessment(@PathParam("projectName") String ProjectId, @PathParam("student") String student) {
        StudentIdentifier userNameentifier = new StudentIdentifier(ProjectId, student);
        return getTotalAssessment(userNameentifier);
    }  //////////dummy/////////////funktioniert wie geplant//////////////////////////////////

    private ArrayList<Performance> getTotalAssessment(StudentIdentifier userNameentifier) {
        return peer.getTotalAssessment(userNameentifier);
    }  /////////dummy/////////////funktioniert wie geplant//////////////////////////////////


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/dummy/totalperformance")
    public List<Performance> getTotalAssessment() {
        List<Performance> result = new ArrayList<>();
        StudentIdentifier student = new StudentIdentifier("projekt", "student");
        List<Integer> quiz = new ArrayList<>();
        quiz.add(1);
        quiz.add(0);
        quiz.add(1);
        quiz.add(0);
        quiz.add(1);
        quiz.add(0);
        quiz.add(1);
        Map<String, Double> work = new HashMap<>();
        work.put("responsibility", 1.);
        work.put("partOfWork", 1.);
        work.put("cooperation", 1.);
        work.put("communication", 1.);
        work.put("autonomous", 1.);
        Map<String, Double> work2 = new HashMap<>();
        work2.put("responsibility", 3.);
        work2.put("partOfWork", 4.);
        work2.put("cooperation", 5.);
        work2.put("communication", 3.);
        work2.put("autonomous", 4.);
        Map<String, Double> contribution1 = new HashMap<>();
        contribution1.put("Dossier", 4.);
        contribution1.put("eJournal", 2.);
        contribution1.put("research", 4.);
        Map<String, Double> contribution2 = new HashMap<>();
        contribution2.put("Dossier", 2.);
        contribution2.put("eJournal", 3.);
        contribution2.put("research", 4.);
        Performance pf = new Performance();
        pf.setContributionRating(contribution1);
        pf.setQuizAnswer(quiz);
        pf.setStudentIdentifier(student);
        pf.setWorkRating(work);
        Performance pf2 = new Performance();
        pf2.setContributionRating(contribution2);
        pf2.setQuizAnswer(quiz);
        pf2.setStudentIdentifier(student);
        pf2.setWorkRating(work2);
        result.add(pf);
        result.add(pf2);
        return result;
    }  /////////dummy////////////returns what i expect it to return!!!!!//////////////////////////////////
}
