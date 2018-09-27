package unipotsdam.gf.core.tasks;

import com.mysql.jdbc.NotImplemented;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.awt.*;

@Path("/tasks")
public class TaskView {


    @Inject
    private TaskDAO taskDAO;

    @Path("/user/{userEmail}/project/{projectToken}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task[] getTasks(@PathParam("userEmail") String userEmail,@PathParam("projectToken") String projectToken)
            throws NotImplemented {
        return taskDAO.getTasks(userEmail, projectToken);
    }
}
