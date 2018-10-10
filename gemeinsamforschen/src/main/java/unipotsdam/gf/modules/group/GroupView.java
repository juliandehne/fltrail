package unipotsdam.gf.modules.group;

import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectConfiguration;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.process.GroupFormationProcess;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Path("/group")
public class GroupView {

    @Inject
    private IGroupFinding groupfinding;

    @Inject
    private Management iManagement;

    @Inject
    ProjectDAO projectDAO;

    @Inject
    GroupFormationProcess groupFormationProcess;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectName}/student/{userName}")
    public ArrayList<String> getStudentsInSameGroup(
            @PathParam("projectName") String projectName, @PathParam("userName") String userName) throws IOException {
        StudentIdentifier student = new StudentIdentifier(projectName, userName);
        return groupfinding.getStudentsInSameGroup(student);
    }


    /**
     * @param name
     * @param groupFindingMechanism
     * @return
     * @throws URISyntaxException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/settings/projects/{projectName}")
    public void createProject(@PathParam("projectName") String name, String groupFindingMechanism)
            throws URISyntaxException {

        try {
            GroupFormationMechanism gfm = GroupFormationMechanism.valueOf(groupFindingMechanism);
            groupFormationProcess.changeGroupFormationMechanism(gfm, new Project(name));
        } catch (Exception e) {
            throw new WebApplicationException(
                    "the groupfindingmechanism needs to be one of " + GroupFormationMechanism.values().toString());
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/projects/{projectName}")
    public void saveGroups(@PathParam("projectName") String projectName, Group[] groups) {
        Project project = new Project(projectName);
        groupFormationProcess.finalizeGroups(groups, project);
    }

}
