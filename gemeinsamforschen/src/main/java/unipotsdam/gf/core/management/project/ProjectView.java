package unipotsdam.gf.core.management.project;

import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.session.GFContexts;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
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
    public String createProject(@Context HttpServletRequest req, Project project) throws URISyntaxException {
        // we assume the token is send not the author id
        String authorToken = project.getAuthorEmail();
        User userByToken = iManagement.getUserByToken(authorToken);
        project.setAuthorEmail(userByToken.getId());
        try {
            String projectToken = iManagement.create(project);
            req.getSession().setAttribute(GFContexts.PROJECTNAME, project.getId());
            return projectToken;
        } catch (Exception e) {
            return "project exists";
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/view/project/{projectName}")
    public Response viewProject(@Context HttpServletRequest req, @PathParam("projectName") String projectName) throws URISyntaxException {
        // we assume the token is send not the author id
        req.getSession().setAttribute(GFContexts.PROJECTNAME, projectName);
        String userEmail = req.getSession().getAttribute(GFContexts.USEREMAIL).toString();
        User user = iManagement.getUserByToken(userEmail);
        if (user.getStudent()){
            return forwardToLocation("project-student.jsp");
        }else{
            return forwardToLocation("project-docent.jsp");
        }

    }

    private Response forwardToLocation(String existsUrl) throws URISyntaxException {
        return Response.seeOther(new URI(existsUrl)).build();
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
    @Path("/all/student/{studentToken}")
    public java.util.List<String> getProjectsStudent(
            @PathParam("studentToken") String studentToken) {
        return iManagement.getProjectsStudent(studentToken);
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/login/{projectId}")
    public String logInProject(@PathParam("projectId") String projectId, @QueryParam("password") String password) {
        Project project = iManagement.getProjectById(projectId);
        if (project == null){
            return "project missing";
        }
        if (!project.getPassword().equals(password) ) {
            return "wrong password";
        }

        String result =  project.getToken();
        return result;
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tags/{projectToken}")
    public String[] getTags(@PathParam("projectToken") String projectToken) {
        // TODO write single query
        Project project = iManagement.getProjectByToken(projectToken);
        return iManagement.getTags(project).toArray(new String[0]);
    }



}
