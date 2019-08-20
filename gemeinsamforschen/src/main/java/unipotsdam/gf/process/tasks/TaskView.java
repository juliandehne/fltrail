package unipotsdam.gf.process.tasks;

import com.google.common.base.Strings;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

@Path("/tasks")
public class TaskView {

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private GFContexts gfContexts;

    @GET
    @Path("/user/{userEmail}/project/{projectToken}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Task> getTasks(@PathParam("userEmail") String userEmail, @PathParam("projectToken") String projectToken)
            throws Exception {
        String user = URLDecoder.decode(userEmail, "UTF-8");
        return taskDAO.getTasks(new User(user), new Project(projectToken));
    }

    @GET
    @Path("progress/projects/{projectName}/task/{taskName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProgressForTask(@Context HttpServletRequest request, @PathParam("projectName") String projectName, @PathParam("taskName") TaskName taskName) {
        if (Strings.isNullOrEmpty(projectName) || taskName == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Project name or taskName was null or empty").build();
        }

        try {
            User user = gfContexts.getUserFromSession(request);
            Project project = new Project(projectName);
            Task task = taskDAO.getUserTask(project, user, taskName);
            if (task == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No task with " + taskName.name() + "found").build();
            }
            return Response.ok(task).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("user not in session").build();
        }
    }
}
