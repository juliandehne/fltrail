package unipotsdam.gf.modules.project;


import org.yaml.snakeyaml.util.UriEncoder;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.ProjectCreationProcess;
import unipotsdam.gf.session.GFContexts;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
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
    public Response createProject(@Context HttpServletRequest req, Project project, @QueryParam("groupSize") Integer groupSize)
            throws IOException {
        String userEmail = gfContexts.getUserEmail(req);
        User user = iManagement.getUserByEmail(userEmail);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        project.setName(UriEncoder.decode(project.getName()));
        try {
            projectCreationProcess.createProject(project, user, groupSize);
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        return Response.ok().build();
    }

    @POST
    @Path("/update/project/{projectName}/groupSize/{groupSize}")
    public String updateGroupSize(@Context HttpServletRequest req, @PathParam("groupSize") Integer groupSize, @PathParam("projectName") String projectName)
            throws Exception {
        Project project = projectDAO.getProjectByName(projectName);
        projectDAO.updateGroupSize(project, groupSize);
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
        User user = new User (studentEmail);
        return iManagement.getProjectsStudent(user);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public java.util.List<Project> getJustStartedProjects() {
        return iManagement.getJustStartedProjects();
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
            @QueryParam("password") String password) throws Exception {
        User user = gfContexts.getUserFromSession(req);
        Project project = projectDAO.getProjectByName(projectName);
        if (project == null) {
            return "project missing";
        }
        projectCreationProcess.studentEntersProject(project, user);

        return "ok";
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/login/{projectName}/password/{password}")
    public Response checkPassword(
            @Context HttpServletRequest req, @PathParam("projectName") String projectName,
            @PathParam("password") String password) throws Exception {
        Project project = projectDAO.getProjectByName(projectName);
        if (project == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (!project.getPassword().equals(password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok().build();
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
