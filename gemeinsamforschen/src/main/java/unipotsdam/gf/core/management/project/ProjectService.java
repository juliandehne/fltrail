package unipotsdam.gf.core.management.project;

import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.ICommunication;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;


@ManagedBean
@Path("/project")
public class ProjectService {


    @Inject
    private Management iManagement;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/create")
    public Response createProject(Project project) throws URISyntaxException {

        iManagement.create(project);
        return Response.ok("project has been created").build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/token")
    public String getProjectToken(
            @QueryParam("project") String projectName, @QueryParam("password") String password)
            throws URISyntaxException {

        String token = iManagement.getProjectToken(projectName, password);
        return token;
    }

  /*  @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/token")
    public String projectExists(
            @QueryParam("project") String projectName, @QueryParam("password") String password)
            throws URISyntaxException {

        String token = iManagement.getProjectToken(projectName, password);
        return token;
    }*/
}
