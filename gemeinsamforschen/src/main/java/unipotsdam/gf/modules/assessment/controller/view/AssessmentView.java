package unipotsdam.gf.modules.assessment.controller.view;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.Assessment;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.assessment.controller.service.PeerAssessmentDummy;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Map;

@Path("/assessments2")
public class AssessmentView {
    private static IPeerAssessment peer = new PeerAssessmentDummy();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/calculate3")
    public Map<StudentIdentifier, Double> calculateAssessment(ArrayList<Performance> totalPerformance) {
        return peer.calculateAssessment(totalPerformance);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/calculate2")
    public String testDesReturn(Assessment assessment) {
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
}
