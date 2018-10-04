package unipotsdam.gf.modules.project;

import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.session.GFContexts;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URISyntaxException;


@ManagedBean
@Path("/project")
public class ProjectView {


    @Inject
    private GFContexts gfContexts;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private Management iManagement;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/create")
    public void createProject(@Context HttpServletRequest req, Project project) throws URISyntaxException, IOException {
        // we assume the token is send not the author id
        String userEmail = gfContexts.getUserEmail(req);
        User user = iManagement.getUserByEmail(userEmail);
        assert user != null;
        if (user == null) {
            throw new IOException("NO user with this email exists in db");
        }
        project.setAuthorEmail(user.getEmail());
        try {
            iManagement.create(project);
        } catch (Exception e) {
            throw new WebApplicationException("Project already exists");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all/author/{userEmail}")
    public String[] getProjects(
            @PathParam("userEmail") String authorToken) {

        java.util.List<String> projects = iManagement.getProjects(authorToken);
        return projects.toArray(new String[0]);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all/student/{studentEmail}")
    public String[] getProjectsStudent(
            @PathParam("studentEmail") String studentEmail) {
        return iManagement.getProjectsStudent(studentEmail).toArray(new String[0]);
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/login/{projectName}")
    public String logInProject(@Context HttpServletRequest req, @PathParam("projectName") String projectName,
                               @QueryParam("password") String
            password) throws IOException {
        User user = gfContexts.getUserFromSession(req);
        Project project = projectDAO.getProjectByName(projectName);
        iManagement.register(user, project, null);
        if (project == null){
            return "project missing";
        }
        if (!project.getPassword().equals(password) ) {
            return "wrong password";
        }



        return "ok";
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/tags/{projectToken}")
    public String[] getTags(@PathParam("projectToken") String projectToken) {
        // TODO write single query
        Project project = iManagement.getProjectByName(projectToken);
        return iManagement.getTags(project).toArray(new String[0]);
    }



}