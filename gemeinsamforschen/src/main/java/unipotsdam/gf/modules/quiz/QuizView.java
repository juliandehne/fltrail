package unipotsdam.gf.modules.quiz;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/quiz")
public class QuizView {
    @Inject
    private IPeerAssessment peer;      //correct DB-conn and stuff

    @Inject
    Management management;

    @Inject
    QuizDAO quizDAO;

    /**
     * possible dimensions:
     * Verantwortungsbewusstsein
     * Disskusionsfähigkeit
     * Anteil am Produkt
     * Kooperationsbereitschaft
     * Selbstständigkeit
     * Führungsqualität
     * Pünktlichkeit
     * Motivation
     * Gewissenhaftigkeit
     * respektvoller Umgang mit anderen
     * Wert der Beiträge
     * kann sich an Vereinbarungen halten
     * emotionale Stabilität
     * Hilfsbereitschaft
     * @param projectName
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/workProperty/project/{projectName}")
    public ArrayList<String> getWorkProperty(@PathParam("projectName") String projectName) {
        ArrayList<String> result = new ArrayList<>();
        result.add("Anteil am Produkt");
        result.add("Verantwortungsbewusstsein");
        result.add("Kooperationsbereitschaft");
        result.add("Diskussionsbereitschaft");
        result.add("Selbstständigkeit");
        return result;
    }


    /**
     *
     * @param projectName
     * @return

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectName}/quiz")
    public ArrayList<Quiz> getQuiz(@PathParam("projectName") String projectName) {
        return peer.getQuiz(projectName);
    }
     */




    /*@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/quizAnswer/projectName/{projectName}/userName/{userName}/")
    public void answerQuiz(Map<String, List<String>> questions, @PathParam("projectName") String projectName, @PathParam("userName") String userName) {
        StudentIdentifier student = new StudentIdentifier(projectName, userName);
        quizDAO.answerQuiz(questions, student);
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
    }*/


  /*  @POST
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
    }*/

}
