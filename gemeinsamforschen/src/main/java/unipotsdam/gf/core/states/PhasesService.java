package unipotsdam.gf.core.states;

import unipotsdam.gf.core.management.Management;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.interfaces.IPhases;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * REST API for switching phases
 * In order to look up the possible phases @see unipotsdam.gf.core.states.ProjectPhase
 */
@Path("/phases")
public class PhasesService  {

    @Inject
    private IPhases phases;

    @Inject
    private Management management;

    /**
     * end phase
     * @param projectPhase
     * @param projectId
     */
    @Path("/{projectPhase}/projects/{projectId}")
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void endPhase(@PathParam("projectPhase") String projectPhase, @PathParam("projectId") String projectId) {
        phases.endPhase(ProjectPhase.valueOf(projectPhase), management.getProjectById(projectId));
    }

    /**
     * get current phase
     * @param projectId
     * @return
     */
    @Path("/projects/{projectId}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getCurrentPhase(@PathParam("projectId") String projectId) {
        return management.getProjectById(projectId).getPhase();
    }
}
