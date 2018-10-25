package unipotsdam.gf.modules.submission.view;

import unipotsdam.gf.modules.annotation.model.Category;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;
import unipotsdam.gf.modules.submission.model.SubmissionPart;
import unipotsdam.gf.modules.submission.model.SubmissionPartPostRequest;
import unipotsdam.gf.modules.submission.model.SubmissionProjectRepresentation;
import unipotsdam.gf.modules.submission.model.SubmissionResponse;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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

    @Inject
    private DossierCreationProcess dossierCreationProcess;

    @Inject
    private SubmissionController submissionController;

    @POST
    @Path("/full")
    public Response addFullSubmission(FullSubmissionPostRequest fullSubmissionPostRequest) {
        // save full submission request in database and return the new full submission
        final FullSubmission fullSubmission = dossierCreationProcess.addSubmission(fullSubmissionPostRequest, new
                User(fullSubmissionPostRequest.getUser()), new Project(fullSubmissionPostRequest.getProjectName()));
        return Response.ok(fullSubmission).build();
    }

    @GET
    @Path("/full/{id}")
    public Response getFullSubmission(@PathParam("id") String fullSubmissionId) {

        // get full submission from database based by id

        FullSubmission fullSubmission = submissionController.getFullSubmission(fullSubmissionId);

        if (fullSubmission != null) {
            return Response.ok(fullSubmission).build();
        } else {
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
        SubmissionPart submissionPart = submissionController.addSubmissionPart(submissionPartPostRequest);

        return Response.ok(submissionPart).build();
    }

    @GET
    @Path("/full/{id}/category/{category}")
    public Response getSubmissionPart(@PathParam("id") String fullSubmissionId, @PathParam("category") String category) {
        // get submission part from database based by id
        SubmissionPart submissionPart = submissionController.getSubmissionPart(fullSubmissionId, Category.valueOf(category.toUpperCase
                ()));

        if (submissionPart != null) {
            return Response.ok(submissionPart).build();
        } else {
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

        ArrayList<SubmissionPart> parts = submissionController.getAllSubmissionParts(fullSubmissionId);

        if (parts.size() > 0) {
            return Response.ok(parts).build();
        } else {
            SubmissionResponse response = new SubmissionResponse();
            response.setMessage("No submission parts found for submission with the id '" + fullSubmissionId + "'");

            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }

    @GET
    @Path("/project/{projectName}")
    public Response getSubmissionPartsByProjectId(@PathParam("projectName") String projectName) {
        // get submission project representation from database based by project id
        ArrayList<SubmissionProjectRepresentation> representations = submissionController.getSubmissionPartsByProjectId(projectName);

        if (representations.size() > 0) {
            return Response.ok(representations).build();
        } else {
            SubmissionResponse response = new SubmissionResponse();
            response.setMessage("No submission parts found for project id '" + projectName + "'");

            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }

    @POST
    @Path("/id/{submissionId}/projects/{projectId}/finalize")
    public void finalize (@PathParam("submissionId") String submissionId, @PathParam("projectId") String projectId,
                          @Context HttpServletRequest req) {
        // TODO implement

        String userEmail = (String) req.getSession().getAttribute(GFContexts.USEREMAIL);
        dossierCreationProcess.finalizeDossier(new FullSubmission(submissionId), new User(userEmail),
                new Project(projectId));
    }
}
