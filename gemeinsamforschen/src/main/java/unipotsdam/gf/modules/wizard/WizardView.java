package unipotsdam.gf.modules.wizard;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/wizard")
public class WizardView {

    @Inject
    private WizardDao wizardDao;

    @Inject
    private Wizard wizard;


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
        wizard.doSpells(TaskName.valueOf(taskName), new Project(projectName));
    }

    @POST
    @Path("/projects/{projectName}/phase/{phase}")
    public void doPhaseSpell(@PathParam("projectName") String projectName, @PathParam("phase") String phase)
            throws Exception {
        wizard.doSpells(Phase.valueOf(phase), new Project(projectName));
    }


}
