package unipotsdam.gf.core.management.project;

import jdk.nashorn.internal.objects.annotations.Getter;
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
public class ProjectView {


    @Inject
    private Management iManagement;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/create")
    public String createProject(Project project) throws URISyntaxException {
        // we assume the token is send not the author id
        String authorToken = project.getAuthor();
        User userByToken = iManagement.getUserByToken(authorToken);
        project.setAuthor(userByToken.getId());
        try {
            String projectToken = iManagement.create(project);
            return projectToken;
        } catch (Exception e) {
            return "project exists";
        }

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

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all/author/{userToken}")
    public java.util.List<String> getProjects(
            @PathParam("userToken") String authorToken) {

        java.util.List<String> projects = iManagement.getProjects(authorToken);
        return projects;
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/join/project/{projectId}/password/{password}")
    public String logInProject(@PathParam("projectId") String projectId, @PathParam("password") String password) {
        Project project = iManagement.getProjectById(projectId);
        if (project == null){
            return "project missing";
        }
        if (project.getPassword().equals(password))
            return "wrong password";

        return "success";
    }


}
