package unipotsdam.gf.modules.groupfinding.view;

import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.groupfinding.GroupfindingImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;

@Path("/group")
public class GroupView {
    private GroupfindingImpl groupfinding = new GroupfindingImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/project/{projectId}/student/{studentId}")
    public ArrayList<String> getStudentsInSameGroup(@PathParam("projectId") String projectId, @PathParam("studentId") String studentId) throws IOException {
        //peer.postPeerRating(peerRatings, projectId);
        StudentIdentifier student = new StudentIdentifier(projectId, studentId);
        return groupfinding.getStudentsInSameGroup(student);
    }

}
