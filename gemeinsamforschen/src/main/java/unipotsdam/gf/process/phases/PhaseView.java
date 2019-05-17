package unipotsdam.gf.process.phases;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.interfaces.IPhases;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

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
     * @param projectPhase which phase are you in
     * @param projectName which project is about to leave a phase
     */
    @Path("/{projectPhase}/projects/{projectName}/end")
    @GET
    public String endPhase(@PathParam("projectPhase") String projectPhase, @PathParam("projectName") String
            projectName) throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
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
     * @param projectName which project is selected
     * @return get the name of the current phase of the project
     */
    @Path("/projects/{projectName}")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String getCurrentPhase(@PathParam("projectName") String projectName) {
        return projectDAO.getProjectByName(projectName).getPhase().toString();
    }
}
