package unipotsdam.gf.modules.journal.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.krb5.internal.MethodData;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.JournalFilter;
import unipotsdam.gf.modules.journal.model.ProjectDescription;
import unipotsdam.gf.modules.journal.service.DummyJournalService;
import unipotsdam.gf.modules.journal.service.DummyProjectDescription;
import unipotsdam.gf.modules.journal.service.JournalService;
import unipotsdam.gf.modules.journal.service.ProjectDescriptionService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.invoke.MethodType;
import java.util.ArrayList;

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
    @Consumes(MediaType.TEXT_HTML)
    @Path("/saveText/{text}")
    public Response saveProjectText(@PathParam("text")String text){
        log.debug(">>> saveText: " + text);

        descriptionService.saveProjectText(text);

        log.debug(">>> saveText");

        return Response.ok().build();
    }

    //save Link
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveLinks/{links}")
    public Response saveProjectLinks(@PathParam("links")String text){
        log.debug(">>> saveLinks: " + text);

        descriptionService.saveProjectLinks(text);

        log.debug(">>> saveLinks");

        return Response.ok().build();
    }

}
