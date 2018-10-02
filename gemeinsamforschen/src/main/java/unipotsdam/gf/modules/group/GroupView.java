package unipotsdam.gf.modules.group;

import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectConfiguration;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

@Path("/group")
public class GroupView {

    @Inject
    private IGroupFinding groupfinding;

    @Inject
    private Management iManagement;

    @Inject
    ProjectDAO projectDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectName}/student/{userName}")
    public ArrayList<String> getStudentsInSameGroup(
            @PathParam("projectName") String projectName, @PathParam("userName") String userName) throws IOException {
        StudentIdentifier student = new StudentIdentifier(projectName, userName);
        return groupfinding.getStudentsInSameGroup(student);
    }


    /**
     *
     * @param projectName
     * @param groupFindingMechanism
     * @return
     * @throws URISyntaxException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/settings/projects/{projectName}")
    public void createProject(@PathParam("projectName") String projectName, String groupFindingMechanism) throws
            URISyntaxException {

        try {
        Project project = projectDAO.getProjectByName(projectName);

         GroupFormationMechanism gfm = GroupFormationMechanism.valueOf(groupFindingMechanism);
            iManagement.create(
                    new ProjectConfiguration(new HashMap<>(), new HashMap<>(), new HashMap<>(), gfm),
                    project);
            assert true;
        } catch (Exception e) {
            throw new WebApplicationException("the groupfindingmechanism needs to be one of "+
                    GroupFormationMechanism.values().toString());
        }

    }

}
