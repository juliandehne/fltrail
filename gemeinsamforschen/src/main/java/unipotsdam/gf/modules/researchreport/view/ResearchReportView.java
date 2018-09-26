package unipotsdam.gf.modules.researchreport.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/researchReport")
public class ResearchReportView {

    Logger log = LoggerFactory.getLogger(ResearchReportView.class);

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/saveResearchReportPart")
    public Response saveResearchReportPart(@FormParam("student") String student,
                                           @FormParam("project") String project, @FormParam("text") String text,
                                           @FormParam("category") String category) {
        log.info("student: {}, project: {}, text: {}, category: {}", student, project, text, category);
        URI location;
        try {
            location = new URI("../researchReport/create-title.jsp?token=" + student);
            return Response.temporaryRedirect(location).build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return Response.ok("Test").build();
    }
}
