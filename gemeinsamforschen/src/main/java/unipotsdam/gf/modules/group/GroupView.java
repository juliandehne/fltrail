package unipotsdam.gf.modules.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.group.learninggoals.PreferenceData;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.GroupFormationProcess;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/group")
public class GroupView {

    @Inject
    private IGroupFinding groupfinding;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private GroupFormationProcess groupFormationProcess;

    @Inject
    private ProfileDAO profileDAO;

    @Inject
    private GFContexts gfContexts;


    /**
     * initializes groups (should only be used in fl context)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all/projects/{projectName}")
    public GroupData getOrInitializeGroups(@PathParam("projectName") String projectName)
            throws WrongNumberOfParticipantsException, JAXBException, JsonProcessingException {
        Project project = new Project(projectName);
        project.setGroupWorkContext(GroupWorkContext.fl);
        return groupFormationProcess.getOrInitializeGroups(project);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectName}/student/{userName}")
    public ArrayList<String> getStudentsInSameGroup(
            @PathParam("projectName") String projectName, @PathParam("userName") String userName){
        User user = new User(userName);
        Project project = new Project(projectName);
        return groupfinding.getStudentsInSameGroup(project, user);
    }


    /**
     * this is serious shitty naming by me
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

    @GET
    @Path("/get/groupId/projects/{projectName}")
    public Integer getMyGroupId(@PathParam("projectName") String projectName, @Context HttpServletRequest req) {
        User user = new User((String) req.getSession().getAttribute("userEmail"));
        return groupfinding.getMyGroupId(user, projectDAO.getProjectByName(projectName));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get/context/{context}")
    public GroupData getGroups(@PathParam("context") String context, @Context HttpServletRequest req) {
        User user = new User((String)req.getSession().getAttribute("userEmail"));
        GroupWorkContext context1 = GroupWorkContext.valueOf(context);
        return new GroupData(groupfinding.getGroups(user, context1));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/projects/{projectName}/groups/save")
    public void finalizeGroups(@PathParam("projectName") String  projectName, Group[] groups,
                               @QueryParam("manipulated") String isManipulated)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException {

        Project project = projectDAO.getProjectByName(projectName);
        project.setGroupWorkContext(profileDAO.getGroupWorkContext(project));
        // wenn gruppen aussehen wie einzelarbeit, dann wird hier umgeschaltet
        groupFormationProcess.testForSingleGroups(groups, isManipulated, project);
        // normaler Prozess hier weiter
        groupFormationProcess.saveGroups(Arrays.asList(groups), project);
        //groupFormationProcess.finalize(project);
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

        //new CompBaseMatcher().sendPreferenceData(projectId, userId, preferenceData);
        groupFormationProcess.sendCompBaseUserData(new Project(projectId), new User(userId), preferenceData);
        return Response.ok().entity("Lernziele werden verarbeitet").build();
    }

}
