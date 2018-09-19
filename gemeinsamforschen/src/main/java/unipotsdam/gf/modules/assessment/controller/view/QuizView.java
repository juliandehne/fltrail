package unipotsdam.gf.modules.assessment.controller.view;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.*;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessment;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectId}/quiz/{quizId}/author/{author}")
    public Quiz getQuiz(@PathParam("projectId") String projectId, @PathParam("quizId") String quizId, @PathParam("author") String author) {
        try {
            String question = java.net.URLDecoder.decode(quizId, "UTF-8");
            return peer.getQuiz(projectId, question, author);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 is unknown");
        }
    }  ///////////////////////////////funktioniert//////////////////////////////////

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectId}/quiz/author/{author}")
    public ArrayList<Quiz> getQuiz(@PathParam("projectId") String projectId, @PathParam("author") String author) {
        return peer.getQuiz(projectId, author);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectId}/quiz")
    public ArrayList<Quiz> getQuiz(@PathParam("projectId") String projectId) {
        return peer.getQuiz(projectId);
    }
    //////////////////////////////////////////funktioniert///////////////////////////////////////


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/peerRating/project/{projectId}")
    public void postPeerRating(ArrayList<PeerRating> peerRatings, @PathParam("projectId") String projectId) throws IOException {
        peer.postPeerRating(peerRatings, projectId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/groupRate/project/{projectId}/student/{studentId}")
    public Integer whichGroupToRate(@PathParam("projectId") String projectId, @PathParam("studentId") String studentId) {
        StudentIdentifier student = new StudentIdentifier(projectId, studentId);
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
    @Path("/quizAnswer/projectId/{projectId}/studentId/{studentId}/")
    public void answerQuiz(Map<String, List<String>> questions, @PathParam("projectId") String projectId, @PathParam("studentId") String studentId) {
        StudentIdentifier student = new StudentIdentifier(projectId, studentId);
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
    @Path("/whatToRate/project/{projectId}/student/{studentId}")
    public String whatToRate(@PathParam("projectId") String projectId, @PathParam("studentId") String studentId) {
        StudentIdentifier student = new StudentIdentifier(projectId, studentId);
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
    @Path("/get/project/{projectId}")
    public Map<StudentIdentifier, Double> getAssessmentForProject(String projectId) {
        return peer.getAssessmentForProject(projectId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/project/{projectId}/student/{studentId}")
    public Map<StudentIdentifier, Double> getAssessmentForStudent(@PathParam("projectId") String projectId, @PathParam("studentId") String studentId) {
        StudentIdentifier student = new StudentIdentifier(projectId, studentId);
        return peer.getAssessmentForStudent(student);
    }  //////////dummy//////////////funktioniert wie geplant//////////////////////////////////


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @Path("/quiz")
    public String createQuiz(StudentAndQuiz studentAndQuiz) {
        ManagementImpl management = new ManagementImpl();
        Project project = management.getProjectById(studentAndQuiz.getStudentIdentifier().getProjectId());
        User user = management.getUserByName(studentAndQuiz.getStudentIdentifier().getStudentId());
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
    @Path("/finalize/project/{projectId}")
    public String calculateAssessment(@PathParam("projectId") String projectId) {
        peer.finalizeAssessment(projectId);
        return "successfully finalized "+projectId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mean/project/{projectId}")
    public int meanOfAssessment(@PathParam("projectId") String ProjectId) {
        return peer.meanOfAssessment(ProjectId);
    }  ///////////////////////////////return 0//////////////////////////////////

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/total/project/{projectId}/student/{student}")
    public ArrayList<Performance> getTotalAssessment(@PathParam("projectId") String ProjectId, @PathParam("student") String student) {
        StudentIdentifier studentIdentifier = new StudentIdentifier(ProjectId, student);
        return getTotalAssessment(studentIdentifier);
    }  //////////dummy/////////////funktioniert wie geplant//////////////////////////////////

    private ArrayList<Performance> getTotalAssessment(StudentIdentifier studentIdentifier) {
        return peer.getTotalAssessment(studentIdentifier);
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
