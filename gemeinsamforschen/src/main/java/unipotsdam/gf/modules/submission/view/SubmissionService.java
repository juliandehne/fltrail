package unipotsdam.gf.modules.submission.view;

import unipotsdam.gf.modules.peer2peerfeedback.Category;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */

@Path("/submissions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubmissionService {

    @POST
    @Path("/full")
    public Response addFullSubmission(FullSubmissionPostRequest fullSubmissionPostRequest) {
        // save full submission request in database and return the new full submission
        SubmissionController controller = new SubmissionController();
        FullSubmission fullSubmission = controller.addFullSubmission(fullSubmissionPostRequest);

        return Response.ok(fullSubmission).build();
    }

    @GET
    @Path("/full/{id}")
    public Response getFullSubmission(@PathParam("id") String fullSubmissionId) {

        // get full submission from database based by id
        SubmissionController controller = new SubmissionController();
        FullSubmission fullSubmission = controller.getFullSubmission(fullSubmissionId);

        if (fullSubmission != null) {
            return Response.ok(fullSubmission).build();
        }
        else {
            // declare response
            SubmissionResponse response = new SubmissionResponse();
            response.setMessage("Submission with the id '" + fullSubmissionId + "' can't be found");

            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

    }

    @POST
    @Path("/part")
    public Response addSubmissionPart(SubmissionPartPostRequest submissionPartPostRequest) {
        // save submission part request in the database and return the new submission part
        SubmissionController controller = new SubmissionController();
        SubmissionPart submissionPart = controller.addSubmissionPart(submissionPartPostRequest);

        return Response.ok(submissionPart).build();
    }

    @GET
    @Path("/full/{id}/category/{category}")
    public Response getSubmissionPart(@PathParam("id") String fullSubmissionId, @PathParam("category") String category) {
        // get submission part from database based by id
        SubmissionController controller = new SubmissionController();
        SubmissionPart submissionPart = controller.getSubmissionPart(fullSubmissionId, Category.valueOf(category.toUpperCase()));

        if (submissionPart != null) {
            return  Response.ok(submissionPart).build();
        }
        else {
            // declare response
            SubmissionResponse response = new SubmissionResponse();
            response.setMessage("Submission part with the full submission id '" + fullSubmissionId + "' and the category '" + category.toUpperCase() + "' can't be found");

            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }

    @GET
    @Path("/full/{id}/parts")
    public Response getAllSubmissionParts(@PathParam("id") String fullSubmissionId) {
        // get submission parts from database based by id
        SubmissionController controller = new SubmissionController();
        ArrayList<SubmissionPart> parts = controller.getAllSubmissionParts(fullSubmissionId);

        if (parts.size() > 0) {
            return Response.ok(parts).build();
        }
        else {
            SubmissionResponse response = new SubmissionResponse();
            response.setMessage("No submission parts found for submission with the id '" + fullSubmissionId + "'");

            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }

    @GET
    @Path("/project/{id}")
    public Response getSubmissionPartsByProjectId(@PathParam("id") String projectId) {
        // get submission project representation from database based by project id
        SubmissionController controller = new SubmissionController();
        ArrayList<SubmissionProjectRepresentation> representations = controller.getSubmissionPartsByProjectId(projectId);

        if (representations.size() > 0) {
            return Response.ok(representations).build();
        }
        else {
            SubmissionResponse response = new SubmissionResponse();
            response.setMessage("No submission parts found for project id '" + projectId + "'");

            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }
}
