package unipotsdam.gf.modules.group;

import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.modules.group.learninggoals.LearningGoalAlgorithm;
import unipotsdam.gf.modules.group.learninggoals.PreferenceData;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.process.GroupFormationProcess;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/group")
public class GroupView {

    @Inject
    private IGroupFinding groupfinding;
    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private GroupFormationProcess groupFormationProcess;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all/projects/{projectName}")
    public GroupData getOrInitializeGroups(@PathParam("projectName") String projectName) {
        return  groupFormationProcess.getOrInitializeGroups(new Project(projectName));
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectName}/student/{userName}")
    public ArrayList<String> getStudentsInSameGroup(
            @PathParam("projectName") String projectName, @PathParam("userName") String userName){
        StudentIdentifier student = new StudentIdentifier(projectName, userName);
        return groupfinding.getStudentsInSameGroup(student);
    }


    /**
     * @param name projectName
     * @param groupFindingMechanism Manual or similarity of learning goals. others will be implemented
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/gfm/updateForUser/projects/{projectName}")
    public void updateGFM(@PathParam("projectName") String name, String groupFindingMechanism) {

        try {
            GroupFormationMechanism gfm = GroupFormationMechanism.valueOf(groupFindingMechanism);
            groupFormationProcess.changeGroupFormationMechanism(gfm, new Project(name));
        } catch (Exception e) {
            throw new WebApplicationException(
                    "the groupfindingmechanism needs to be one of " + Arrays.toString(GroupFormationMechanism.values()));
        }

    }

    /**
     * @param name projectName
     * @param groupFindingMechanism Manual, similarity of learning goals or others that are going to be implemented
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/gfm/create/projects/{projectName}")
    public void createGFM(@PathParam("projectName") String name, String groupFindingMechanism) {

        try {
            GroupFormationMechanism gfm = GroupFormationMechanism.valueOf(groupFindingMechanism);
            groupFormationProcess.setGroupFormationMechanism(gfm, new Project(name));
        } catch (Exception e) {
            throw new WebApplicationException(
                    "the groupfindingmechanism needs to be one of " + Arrays.toString(GroupFormationMechanism.values()));
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/gfm/projects/{projectName}")
    public Map<String, String> getGFM(@PathParam("projectName") String projectName) {
        Project project = projectDAO.getProjectByName(projectName);
        GroupFormationMechanism gfm = groupFormationProcess.getGFMByProject(project);
        Map<String,String> result = new HashMap<>();
        result.put("gfm", gfm.toString());
        return result;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/projects/{projectName}")
    public List<Group> getGroups(@PathParam("projectName") String projectName) {
        return groupfinding.getGroups(projectDAO.getProjectByName(projectName));
    }



    /**
     * find out if this is used by learning goal
     */
    @Deprecated
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/projects/{projectName}")
    public void saveGroups(@PathParam("projectName") String projectName, Group[] groups) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        Project project = new Project(projectName);
        groupFormationProcess.saveGroups(Arrays.asList(groups), project);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/projects/{projectName}/groups")
    public void persistGroups(@PathParam("projectName") String  projectName, GroupData data) throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        Project project = new Project(projectName);
        groupFormationProcess.saveGroups(data.getGroups(), project);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/projects/{projectName}/groups/finalize")
    public void finalizeGroups(@PathParam("projectName") String  projectName)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {
        Project project = new Project(projectName);
        groupFormationProcess.finalize(project);
    }

    /**
     * needed for the learning goal algorithm
     * @param projectId this is actually the project name
     * @param userId this is actually the userEmail
     * @param preferenceData  learning goals, research question and tags selected
     * @return plain text response
     */
    @Path("/user/{userId}/projects/{projectId}/preferences")
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    public Response createPreferences(
            @PathParam("projectId") String projectId, @PathParam("userId") String userId, PreferenceData preferenceData)
            throws Exception {

        new LearningGoalAlgorithm().sendPreferenceData(projectId, userId, preferenceData);
        return Response.ok().entity("Lernziele werden verarbeitet").build();
    }

}
