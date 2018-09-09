package unipotsdam.gf.core.states;

import unipotsdam.gf.core.management.project.ProjectDAO;
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
 * In order to look up the possible phases @see unipotsdam.gf.core.states.ProjectPhase
 */
@Path("/phases")
public class PhasesService  {

    @Inject
    private IPhases phases;

    @Inject
    private ProjectDAO projectDAO;

    /**
     * end phase
     * @param projectPhase
     * @param projectId
     */
    @Path("/{projectPhase}/projects/{projectId}")
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void endPhase(@PathParam("projectPhase") String projectPhase, @PathParam("projectId") String projectId) {
        phases.endPhase(ProjectPhase.valueOf(projectPhase), projectDAO.getProjectById(projectId));
    }

    /**
     * get current phase
     * @param projectId
     * @return
     */
    @Path("/{projectPhase}/projects/{projectId}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getCurrentPhase(@PathParam("projectId") String projectId) {
        return projectDAO.getProjectById(projectId).getPhase();
    }
}
