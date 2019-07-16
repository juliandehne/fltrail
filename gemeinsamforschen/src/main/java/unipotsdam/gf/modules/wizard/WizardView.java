package unipotsdam.gf.modules.wizard;

import jdk.nashorn.internal.objects.annotations.Getter;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Path("/wizard")
public class WizardView {

    @Inject
    private WizardDao wizardDao;

    @Inject
    private Wizard wizard;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private UserDAO userDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/projects")
    public java.util.List<WizardProject> getProjects() {
        return wizard.getProjects();
    }

    @POST
    @Path("/projects/{projectName}/task/{taskName}")
    public void doTaskSpell(@PathParam("projectName") String projectName, @PathParam("taskName") String taskName)
            throws Exception {
        Project projectByName = projectDAO.getProjectByName(projectName);
        wizard.doSpells(TaskName.valueOf(taskName), projectByName);
    }

    @POST
    @Path("/projects/{projectName}/phase/{phase}")
    public void doPhaseSpell(@PathParam("projectName") String projectName, @PathParam("phase") String phase)
            throws Exception {
        Project projectByName = projectDAO.getProjectByName(projectName);
        wizard.doSpells(Phase.valueOf(phase), projectByName);
    }

    @GET
    @Path("/projects/{projectName}/tasksFinished")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public java.util.List<String> getFinishedRelevantTasks(@PathParam("projectName") String projectName)
            throws Exception {

        Project projectByName = projectDAO.getProjectByName(projectName);
        List<TaskName> wizardrelevantTaskStatus = wizardDao.getWizardrelevantTaskStatus(projectByName);
        return wizardrelevantTaskStatus.stream().map(Enum::name).collect(Collectors.toList());
    }

    @GET
    @Path("/projects/{projectName}/state")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public HashMap<TaskName, Progress> getRelevantTasksStatus(@PathParam("projectName") String projectName)
            throws Exception {
        Project projectByName = projectDAO.getProjectByName(projectName);
        HashMap<TaskName, Progress> wizardrelevantTaskStatus = wizardDao.getWizardrelevantTaskMap(projectByName);
        return wizardrelevantTaskStatus;
    }


    @GET
    @Path("/projects/{projectName}/teststudent")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getTestStudent(@PathParam("projectName") String projectName) throws Exception {
        List<User> usersByProjectName = userDAO.getUsersByProjectName(projectName);
        if (usersByProjectName != null  && usersByProjectName.size() > 0) {
            return usersByProjectName.iterator().next();
        }
        return null;
    }


}
