package unipotsdam.gf.process.tasks;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@Path("/tasks")
public class TaskView {


    @Inject
    private TaskDAO taskDAO;

    @GET
    @Path("/user/{userEmail}/project/{projectToken}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Task> getTasks(@PathParam("userEmail") String userEmail, @PathParam("projectToken") String projectToken)
            throws UnsupportedEncodingException {
        String user = java.net.URLDecoder.decode(userEmail, "UTF-8");
        return taskDAO.getTasks(new User(user), new Project(projectToken));
    }
}
