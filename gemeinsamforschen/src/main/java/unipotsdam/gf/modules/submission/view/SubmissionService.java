package unipotsdam.gf.modules.submission.view;

import unipotsdam.gf.modules.annotation.model.AnnotationResponse;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;
import unipotsdam.gf.modules.submission.model.SubmissionResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        // save full submission request in database and return the new id
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
}
