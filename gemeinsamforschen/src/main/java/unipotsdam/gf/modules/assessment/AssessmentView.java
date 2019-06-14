package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.process.PeerAssessmentProcess;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/assessment")
public class AssessmentView {

    @Inject
    private PeerAssessmentProcess peerAssessmentProcess;

    @POST
    @Path("/grading/start/projects/{projectName}")
    public void startGrading(@PathParam("projectName") String projectName){
        peerAssessmentProcess.startGrading(new Project(projectName));
    }
}
