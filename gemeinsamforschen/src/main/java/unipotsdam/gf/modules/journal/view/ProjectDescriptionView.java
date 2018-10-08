package unipotsdam.gf.modules.journal.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.ProjectDescription;
import unipotsdam.gf.modules.journal.service.ProjectDescriptionImpl;
import unipotsdam.gf.modules.journal.service.ProjectDescriptionService;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * View for the project description
 *
 */

@Path("/projectdescription")
public class ProjectDescriptionView {

    private final Logger log = LoggerFactory.getLogger(ProjectDescriptionView.class);
    private final ProjectDescriptionService descriptionService = new ProjectDescriptionImpl();

    //get Description
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{project}/{student}")
    public Response getProjectDescription(@PathParam("project") String project, @PathParam("student") String student) {
        log.debug(">>> getProjectDescription: " + project + "/" + student);

        ProjectDescription result = descriptionService.getProjectByStudent(new StudentIdentifier(project, student));

        log.debug(">>> getProjectDescription");
        return Response.ok(result).build();
    }

    //save Description
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/saveText")
    public Response saveProjectText(@FormParam("student") String student, @FormParam("project") String project, @FormParam("text") String text) {
        log.debug(">>> saveText: " + text);
        descriptionService.saveProjectText(new StudentIdentifier(project, student), text);

        try {
            URI location = new URI("../pages/eportfolio.jsp");
            log.debug("<<< saveText: redirect to "  +location.toString());
            return Response.temporaryRedirect(location).build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.debug("saveText: redirect failed" );
        }

        log.debug("<<< saveText");
        log.debug(">>> saveText");

        return Response.ok().build();
    }

    //add Link
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/addLink")
    public Response addLink(@FormParam("link") String link, @FormParam("name") String name, @FormParam("projectdescriptionId") String project) {
        log.debug(">>> addLink: " + name + ":" + link);

        ProjectDescription desc = descriptionService.getProjectById(project);
        descriptionService.addLink(project, link, name);


        try {
            URI location = new URI("../pages/eportfolio.jsp" + desc.getStudent().getUserEmail() + "&projectName=" + desc.getStudent().getProjectName());
            log.debug("<<< addLink: redirect to "  +location.toString());
            return Response.temporaryRedirect(location).build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.debug("addLink: redirect failed" );
        }

        log.debug(">>> addLink");

        return Response.ok().build();
    }


    //delete Link
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/deleteLink")
    public Response deleteLink(String link){
        log.debug(">>> deleteLink: " + link);

        descriptionService.deleteLink(link);
        try {
            URI location = new URI("../pages/eportfolio.jsp");
            log.debug("<<< deleteLink: redirect to "  +location.toString());
            return Response.temporaryRedirect(location).build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.debug("deleteLink: redirect failed" );
        }

        log.debug("<<< deleteLink");
        return Response.ok().build();
    }

    //close descr
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/close")
    public Response closeDescription(String desc){
        log.debug(">>> closeDescription: " + desc);

        StudentIdentifier student = descriptionService.getProjectById(desc).getStudent();
        descriptionService.closeDescription(desc);
        try {
            URI location = new URI("../pages/eportfolio.jsp" + student.getUserEmail() + "&projectName=" + student.getProjectName());
            log.debug("<<< closeDescription: redirect to "  +location.toString());
            return Response.temporaryRedirect(location).build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.debug("closeDescription: redirect failed" );
        }

        log.debug("<<< closeDescription");
        return Response.ok().build();
    }

}
