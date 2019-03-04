package unipotsdam.gf.modules.project;


import org.yaml.snakeyaml.util.UriEncoder;
import unipotsdam.gf.process.ProjectCreationProcess;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
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
import java.util.List;


@ManagedBean
@Path("/project")
public class ProjectView {


    @Inject
    private GFContexts gfContexts;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private Management iManagement;

    @Inject
    private ProjectCreationProcess projectCreationProcess;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/create")
    public String createProject(@Context HttpServletRequest req, Project project)
            throws IOException, RocketChatDownException, UserDoesNotExistInRocketChatException {
        String userEmail = gfContexts.getUserEmail(req);
        User user = iManagement.getUserByEmail(userEmail);
        assert user != null;
        if (user == null) {
            throw new IOException("NO user with this email exists in db");
        }
        project.setName(UriEncoder.decode(project.getName()));
        projectCreationProcess.createProject(project, user);
        return "success";
    }

    @POST
    @Path("/delete/project/{projectName}")
    public String deleteProject(@Context HttpServletRequest req, @PathParam("projectName") String projectName)
            throws IOException, RocketChatDownException, UserDoesNotExistInRocketChatException {
        String userEmail1 = gfContexts.getUserEmail(req);
        User user = iManagement.getUserByEmail(userEmail1);
        Boolean isStudent= user.getStudent();
        String userEmail = gfContexts.getUserEmail(req);
        Project project;
        try{
            project = projectDAO.getProjectByName(projectName);
        }catch(Exception e){
            return "project missing";
        }
        if (!isStudent){
            if (project.getAuthorEmail().equals(userEmail)){
                projectCreationProcess.deleteProject(project);
            }else{
                return "not author";
            }
        }else{
            return "no permission";
        }
        return "success";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all/author/{userEmail}")
    public List<Project> getProjects(
            @PathParam("userEmail") String authorToken) {
        return iManagement.getProjects(authorToken);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all/student/{studentEmail}")
    public java.util.List<Project> getProjectsStudent(
            @PathParam("studentEmail") String studentEmail) {
        return iManagement.getProjectsStudent(studentEmail);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public java.util.List<Project> getProjects() {
        return iManagement.getAllProjects();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/not/student/{studentEmail}")
    public java.util.List<Project> getProjectsStudentIsNotPartOf(
            @PathParam("studentEmail") String studentEmail) {
        return projectDAO.getAllProjectsExceptStudents(new User(studentEmail));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all/like/{searchString}")
    public java.util.List<Project> getProjectsBySearchstring(
            @PathParam("searchString") String searchString) {
        return projectDAO.getProjectsLike(searchString);
    }



    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/login/{projectName}")
    public String register(
            @Context HttpServletRequest req, @PathParam("projectName") String projectName,
            @QueryParam("password") String password)
            throws IOException, RocketChatDownException, UserDoesNotExistInRocketChatException {
        User user = gfContexts.getUserFromSession(req);
        Project project = projectDAO.getProjectByName(projectName);
        if (project == null) {
            return "project missing";
        }
        /*
        if (!project.getPassword().equals(password)) {
            return "wrong password";
        }
        */
        // TODO, this should not be called here
        projectCreationProcess.studentEntersProject(project, user);

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
