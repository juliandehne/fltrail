package unipotsdam.gf.modules.assessment.controller.view;

import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.Assessment;
import unipotsdam.gf.modules.assessment.controller.model.*;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessment;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessmentDummy;


import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/assessments2")
public class AssessmentView implements IPeerAssessment{
    private static IPeerAssessment peer =  new PeerAssessmentDummy();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/calculate3")
    @Override
    public List<Grading> calculateAssessment(ArrayList<Performance> totalPerformance) {
        return peer.calculateAssessment(totalPerformance);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/calculate2")
    public String testDesReturn(Assessment assessment){
        Assessment shuttle = new Assessment();  //neues Objekt, dass dann bearbeitet werden kann
        //System.out.println(assessment.getBewertung());
        shuttle.setAssessment(assessment);      //inhalte werden in die DB geschrieben und es wird erfolg zurückgemeldet
        return "1";
    }
    /*}
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("dummy/calculate2")
    public Assessment dummyAssessment(){
        StudentIdentifier student = new StudentIdentifier("projectID", "StudentId");
        Performance performance;
        StudentIdentifier bewertender;
        String projektId;
        int bewertung;
         boolean adressat;
        Date deadline;
        Assessment shuttle = new Assessment(student, )

        return null;
    }*/

    @Override
    public void addAssessmentDataToDB(Assessment assessment) {
    }
    @Override
    public Quiz getQuiz(String projectId, String groupId) {
        return null;
    }
    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student) {
        return null;
    }
    @Override
    public void createQuiz(StudentAndQuiz studentAndQuiz) {
    }
    @Override
    public ArrayList<Performance> getTotalAssessment(StudentIdentifier studentIdentifier) {
        return null;
    }
    @Override
    public int meanOfAssessement(String ProjectId) {
        return 0;
    }
    @Override
    public ArrayList<Quiz> getQuiz(String projectId) {
        return null;
    }
    @Override
    public void postPeerRating(ArrayList<PeerRating> peerRatings, String projectId, String groupId) {
    }
}
