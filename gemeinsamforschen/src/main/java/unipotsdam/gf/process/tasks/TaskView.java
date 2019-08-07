package unipotsdam.gf.process.tasks;

import com.google.common.base.Strings;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.IExecutionProcess;
import unipotsdam.gf.process.tasks.progress.GroupTaskProgress;
import unipotsdam.gf.process.tasks.progress.TaskProgress;
import unipotsdam.gf.process.tasks.progress.UserTaskProgress;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Path("/tasks")
public class TaskView {

    @Inject
    private TaskDAO taskDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private IExecutionProcess executionProcess;

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
    public Response getProgressForTask(@PathParam("projectName") String projectName, @PathParam("taskName") TaskName taskName) {
        if (Strings.isNullOrEmpty(projectName) || taskName == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Project name or taskName was null or empty").build();
        }
        Project project = new Project(projectName);
        List<Task> tasks = taskDAO.getTaskForProjectByTaskName(project, taskName);

        if (tasks.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No task with " + taskName.name() + "found").build();
        }
        ArrayList<TaskProgress> taskProgressList = new ArrayList<>();
        tasks.forEach(task -> {
            String userEmail = task.getUserEmail();
            TaskProgress taskProgress;
            if (Strings.isNullOrEmpty(userEmail)) {
                taskProgress = new GroupTaskProgress(task, task.getGroupTask());
            } else {
                User user = userDAO.getUserByEmail(task.getUserEmail());
                taskProgress = new UserTaskProgress(task, user);
            }
            taskProgressList.add(taskProgress);
        });
        return Response.ok(taskProgressList).build();
    }
}
