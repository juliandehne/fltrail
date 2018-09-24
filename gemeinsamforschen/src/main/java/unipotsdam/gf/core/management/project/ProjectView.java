package unipotsdam.gf.core.management.project;

import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.user.User;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/login/{projectId}/password/{password}")
    public String logInProject(@PathParam("projectId") String projectId, @PathParam("password") String password) {
        Project project = iManagement.getProjectById(projectId);
        if (project == null){
            return "project missing";
        }
        if (project.getPassword().equals(password) || project.getPassword().trim().equals(""))
            return "wrong password";

        String result =  project.getToken();
        return result;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tags/{projectToken}")
    public java.util.List<String> getTags(@PathParam("projectToken") String projectToken) {
        // TODO write single query
        Project project = iManagement.getProjectByToken(projectToken);
        return iManagement.getTags(project);
    }



}
