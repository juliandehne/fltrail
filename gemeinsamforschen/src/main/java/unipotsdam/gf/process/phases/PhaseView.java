package unipotsdam.gf.process.phases;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.process.GroupFormationProcess;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST API for switching phases
 * In order to look up the possible phases @see unipotsdam.gf.core.states.model.Phase
 */
@Path("/phases")
public class PhaseView {



    @Inject
    IPhases phases;

    @Inject
    private ProjectDAO projectDAO;

    /**
     * end phase
     *
     * @param projectPhase
     * @param projectName
     */
    @Path("/{projectPhase}/projects/{projectName}/end")
    @GET
    public String endPhase(@PathParam("projectPhase") String projectPhase, @PathParam("projectName") String
            projectName) throws URISyntaxException {
        Phase phase = Phase.valueOf(projectPhase);
        Project project = projectDAO.getProjectByName(projectName);
        phases.endPhase(phase, project);
        // just hacked this for
        return "ok";
        //return Response.temporaryRedirect(new URI(".")).build();
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
