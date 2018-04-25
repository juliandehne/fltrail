package uzuzjmd.competence.service;


import datastructures.lists.StringList;
import io.swagger.annotations.ApiOperation;
import uzuzjmd.competence.algorithms.groups.drools.GroupFinding2;
import uzuzjmd.competence.persistence.SQLSimilarityAlgorithmStorage;
import uzuzjmd.competence.persistence.SimilarityAlgorithmStorage;
import uzuzjmd.competence.shared.group.GroupData;
import uzuzjmd.competence.shared.group.PreferenceData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by dehne on 04.12.2017.
 */
@Path("/api2")
public class GroupApiImpl {


    @ApiOperation(value = "get group composition based on similarity algorithm", notes = "It is advisable, to use this interface regularly instead of " +
            "uploading all the information at once in order to reduce loading times with the group composition " +
            "algorithm! Projects can be equivalent to courses in the lms if they share the same id!")
    @Path("/user/{userId}/projects/{projectId}/preferences")
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    public Response createPreferences(
            @PathParam("projectId") String projectId, @PathParam("userId") String userId, PreferenceData preferenceData)
            throws Exception {

        SimilarityAlgorithmStorage storage = new SQLSimilarityAlgorithmStorage();
        storage.addPreferences(projectId, userId, preferenceData);

    /*    CourseContext courseContext = new CourseContext(projectId);
        courseContext.persist();

        uzuzjmd.competence.persistence.dao.User user = new uzuzjmd.competence.persistence.dao.User(userId);
        user.persist();
        user.addCourseContext(courseContext);


        for (String competence : preferenceData.getCompetences()) {
            Competence competenceDAO = new Competence(competence);
            competenceDAO.isResearch = false;
            competenceDAO.persist();
            competenceDAO.addCourseContext(courseContext);
            user.createEdgeWith(Edge.InterestedInCompetence, competenceDAO);
        }

        for (String competence : preferenceData.getResearchQuestions()) {
            Competence competenceDAO = new Competence(competence);
            competenceDAO.isResearch = true;
            competenceDAO.persist();
            competenceDAO.addCourseContext(courseContext);
            user.createEdgeWith(Edge.InterestedInCompetence, competenceDAO);
        }

        List<String> tagsSelected = preferenceData.getTagsSelected();
        for (String s : tagsSelected) {
            Catchword catchword = new Catchword(s);
            catchword.persist();
            user.createEdgeWith(Edge.InterestedInCatchword, catchword);
        }*/

        return Response.ok().entity("Lernziele werden verarbeitet").build();
    }



    @ApiOperation(value = "get projec ids associated with a user", notes = "")
    @Path("/user/{userId}/projects")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public StringList getProjects(@PathParam("userId") String userId) throws Exception {
        // TODO implement

        /*User userDao = new User(userId);
        List<String> projects = userDao.getAssociatedDaoIdsAsRange(Edge.CourseContextOfUser);
        StringList stringList = new StringList();
        stringList.setData(projects);*/
        return null;
    }

    @ApiOperation(value = "get group composition based on similarity algorithm", notes = "Returns a warning, if the " +
            "group composition is still being processed. Assert that more then 5 students are taking part in the " +
            "project and have been added with /api2/user/{userId}/projects/{projectId}/preferences")
    @Path("/groups/{projectId}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public GroupData getGroups(@PathParam("projectId") String projectId) {
        GroupFinding2 groupFinding2 = new GroupFinding2();

        return groupFinding2.convert(projectId);
    }
}
