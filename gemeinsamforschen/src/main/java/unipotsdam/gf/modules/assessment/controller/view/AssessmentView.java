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
import java.util.List;

@Path("/assessments")
public class AssessmentView implements IPeerAssessment{
    private static IPeerAssessment peer =  new PeerAssessmentDummy();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/calculate2")
    @Override
    public List<Grading> calculateAssessment(ArrayList<Performance> totalPerformance) {
        return peer.calculateAssessment(totalPerformance);
    }
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
