package unipotsdam.gf.modules.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
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
import java.util.List;

@Path("/group")
public class GroupView {

    @Inject
    private IGroupFinding groupfinding;

    @Inject
    private Management iManagement;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private GroupFormationProcess groupFormationProcess;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all/projects/{projectName}")
    public GroupData getOrInitializeGroups(@PathParam("projectName") String projectName) {
        GroupData data = groupFormationProcess.getOrInitializeGroups(new Project(projectName));
        return  data;
    }


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
    @Path("/gfm/updateForUser/projects/{projectName}")
    public void updateGFM(@PathParam("projectName") String name, String groupFindingMechanism)
            throws URISyntaxException {

        try {
            GroupFormationMechanism gfm = GroupFormationMechanism.valueOf(groupFindingMechanism);
            groupFormationProcess.changeGroupFormationMechanism(gfm, new Project(name));
        } catch (Exception e) {
            throw new WebApplicationException(
                    "the groupfindingmechanism needs to be one of " + GroupFormationMechanism.values().toString());
        }

    }

    /**
     * @param name
     * @param groupFindingMechanism
     * @return
     * @throws URISyntaxException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/gfm/create/projects/{projectName}")
    public void createGFM(@PathParam("projectName") String name, String groupFindingMechanism)
            throws URISyntaxException {

        try {
            GroupFormationMechanism gfm = GroupFormationMechanism.valueOf(groupFindingMechanism);
            groupFormationProcess.setGroupFormationMechanism(gfm, new Project(name));
        } catch (Exception e) {
            throw new WebApplicationException(
                    "the groupfindingmechanism needs to be one of " + GroupFormationMechanism.values().toString());
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/projects/{projectName}")
    public List<Group> getGroups(@PathParam("projectName") String projectName) {
        List<Group> result = groupfinding.getGroups(projectDAO.getProjectByName(projectName));
        return result;
    }



    /**
     * find out if this is used by learning goal
     * @param projectName
     * @param groups
     */
    @Deprecated
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/projects/{projectName}")
    public void saveGroups(@PathParam("projectName") String projectName, Group[] groups) {
        Project project = new Project(projectName);
        //groupformationprocess.saveGroups(groups)
        //delete:
        groupfinding.persistGroups(Arrays.asList(groups), project);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/projects/{projectName}/groups")
    public void persistGroups(@PathParam("projectName") String  projectName, GroupData data) {
        Project project = new Project(projectName);

        groupfinding.persistGroups(data.getGroups(), project);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/projects/{projectName}/groups/finalize")
    public void finalizeGroups(@PathParam("projectName") String  projectName) {
        Project project = new Project(projectName);
        groupFormationProcess.finalize(project);
    }

}
