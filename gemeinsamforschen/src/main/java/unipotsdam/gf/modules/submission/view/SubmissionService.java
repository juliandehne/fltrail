package unipotsdam.gf.modules.submission.view;

import com.itextpdf.text.DocumentException;
import unipotsdam.gf.modules.annotation.model.Category;
import unipotsdam.gf.modules.assessment.controller.model.Categories;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.*;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Inject
    private UserDAO userDAO;

    @Inject
    private GFContexts gfContexts;


    @POST
    @Path("/full")
    public Response addFullSubmission(@Context HttpServletRequest req,
                                      FullSubmissionPostRequest fullSubmissionPostRequest) {
        // save full submission request in database and return the new full submission

        final FullSubmission fullSubmission;
        String userEmail = (String) req.getSession().getAttribute(GFContexts.USEREMAIL);
        User user = userDAO.getUserByEmail(userEmail);
        try {
            fullSubmission = dossierCreationProcess.addSubmission(fullSubmissionPostRequest, user,
                    new Project(fullSubmissionPostRequest.getProjectName()));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
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

    @GET
    @Path("/full/groupId/{groupId}/project/{projectName}")
    public Response getFullSubmission(@PathParam("projectName") String projectName,
                                      @PathParam("groupId") Integer groupId
    ) throws IOException {
        Project project = new Project(projectName);
        String fullSubmissionId = submissionController.getFullSubmissionId(groupId, project);

        // get full submission from database based by id
        return getFullSubmission(fullSubmissionId);
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
    public void finalize(@PathParam("submissionId") String submissionId, @PathParam("projectId") String projectId,
                         @Context HttpServletRequest req) {
        String userEmail = (String) req.getSession().getAttribute(GFContexts.USEREMAIL);
        dossierCreationProcess.finalizeDossier(new FullSubmission(submissionId), new User(userEmail),
                new Project(projectId));
    }

    @GET
    @Path("categories/project/{projectName}")
    public List<String> getAnnotationCategories(@PathParam("projectName") String projectName) {
        //todo: for every project categories should be selectable
        return Categories.standardAnnotationCategories;
    }

}
