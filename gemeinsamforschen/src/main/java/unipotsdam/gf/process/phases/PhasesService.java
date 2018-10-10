package unipotsdam.gf.process.phases;

import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.interfaces.IPhases;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST API for switching phases
 * In order to look up the possible phases @see unipotsdam.gf.core.states.model.Phase
 */
@Path("/phases")
public class PhasesService {

    private IPhases phases = new PhasesImpl();

    @Inject
    private ProjectDAO projectDAO;

    /**
     * end phase
     *
     * @param projectPhase
     * @param projectName
     */
    @Path("/{projectPhase}/projects/{projectName}")
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void endPhase(@PathParam("projectPhase") String projectPhase, @PathParam("projectName") String projectName) {
        phases.endPhase(Phase.valueOf(projectPhase), projectDAO.getProjectByName(projectName));
    }

    /**
     * get current phase
     *
     * @param projectName
     * @return
     */
    @Path("/projects/{projectName}")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String getCurrentPhase(@PathParam("projectName") String projectName) {
        return projectDAO.getProjectByName(projectName).getPhase().toString();
    }
}
