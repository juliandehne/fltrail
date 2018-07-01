package unipotsdam.gf.modules.journal.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.journal.model.ProjectDescription;
import unipotsdam.gf.modules.journal.service.DummyProjectDescription;
import unipotsdam.gf.modules.journal.service.ProjectDescriptionService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * View for the project description
 *
 * TODO error handling
 */

@Path("/projectdescription")
public class ProjectDescriptionView {

    Logger log = LoggerFactory.getLogger(ProjectDescriptionView.class);
    ProjectDescriptionService descriptionService = new DummyProjectDescription();

    //get Description
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{project}")
    public Response getProjectDescription(@PathParam("project") String project){
        log.debug(">>> getProjectDescription: " + project);

        ProjectDescription result = descriptionService.getProject(project);

        log.debug(">>> getProjectDescription");
        return Response.ok(result).build();
    }

    //save Description
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/saveText")
    public Response saveProjectText(@FormParam("student")String student,@FormParam("project")String project,@FormParam("text")String text){
        log.debug(">>> saveText: " + text);

        descriptionService.saveProjectText(text);

        //TODO token
        try {
            URI location = new URI("../pages/eportfolio.jsp?token=test");
            log.debug("<<< saveText: redirect to "  +location.toString());
            return Response.temporaryRedirect(location).build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.debug("saveText: redirect failed" );
        }

        log.debug("<<< saveText");log.debug(">>> saveText");

        return Response.ok().build();
    }

    //add Link
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/addLink")
    public Response addLink(@FormParam("link") String link, @FormParam("name") String name){
        log.debug(">>> addLink: " + name + ":" + link);

        descriptionService.addLink(link, name );


        try {
            URI location = new URI("../pages/eportfolio.jsp?token=test");
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
        //TODO token
        try {
            URI location = new URI("../pages/eportfolio.jsp?token=test");
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
    @Path("/close")
    public Response closeDescription(String desc){
        log.debug(">>> closeDescription: " + desc);

        descriptionService.closeDescription(desc);
        //TODO token
        try {
            URI location = new URI("../pages/eportfolio.jsp?token=test");
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
