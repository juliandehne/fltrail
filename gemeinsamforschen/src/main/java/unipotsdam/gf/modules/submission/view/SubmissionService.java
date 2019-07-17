package unipotsdam.gf.modules.submission.view;

import com.itextpdf.text.DocumentException;
import unipotsdam.gf.modules.assessment.controller.model.Categories;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.submission.model.*;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.process.tasks.TaskName;
import unipotsdam.gf.session.GFContexts;
import unipotsdam.gf.session.Lock;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private Lock lock;

    @Inject
    private ProjectDAO projectDAO;


    @POST
    @Path("/full")
    public Response addFullSubmission(
            @Context HttpServletRequest req, FullSubmissionPostRequest fullSubmissionPostRequest) {
        // save full submission request in database and return the new full submission

        String userEmail;
        try {
            userEmail = gfContexts.getUserEmail(req);
            User user = userDAO.getUserByEmail(userEmail);
            Project project = new Project(fullSubmissionPostRequest.getProjectName());


            final FullSubmission fullSubmission = dossierCreationProcess.addDossier(fullSubmissionPostRequest, user, project);
            lock.deleteLockInDB(TaskName.UPLOAD_DOSSIER, groupDAO.getGroupByGroupId(fullSubmission.getGroupId()));
            return Response.ok(fullSubmission).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("user email is not in context").build();
        }

    }

    @GET
    @Path("/full/{id}")
    public Response getFullSubmission(@PathParam("id") String fullSubmissionId) {
        // get full submission from database based by id
        FullSubmission fullSubmission = submissionController.getFullSubmission(fullSubmissionId);
        return Response.ok(fullSubmission).build();
    }

    @GET
    @Path("/full/groupId/{groupId}/project/{projectName}/fileRole/{fileRole}")
    public Response getFullSubmission(
            @Context HttpServletRequest req, @PathParam("projectName") String projectName,
            @PathParam("groupId") Integer groupId, @PathParam("fileRole") FileRole fileRole,
            @QueryParam("version") Integer version) {
        Project project = new Project(projectName);
        FullSubmission fullSubmission = submissionController.getFullSubmissionBy(groupId, project, fileRole, version);
        if (fullSubmission == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No submission found!").build();
        }
        return Response.ok(fullSubmission).build();
    }

    @POST
    @Path("/full/update")
    public Response updateFullSubmission(
            @Context HttpServletRequest req, FullSubmissionPostRequest fullSubmissionPostRequest,
            @QueryParam("finalize") Boolean finalize) throws IOException, DocumentException {
        String userEmail = (String) req.getSession().getAttribute(GFContexts.USEREMAIL);
        User user = userDAO.getUserByEmail(userEmail);

        // save full submission request in database and return the new full submission
        final FullSubmission fullSubmission = dossierCreationProcess.updateSubmission(fullSubmissionPostRequest, user,
                new Project(fullSubmissionPostRequest.getProjectName()), finalize);
        lock.deleteLockInDB(TaskName.UPLOAD_DOSSIER, groupDAO.getGroupByGroupId(fullSubmission.getGroupId()));
        return Response.ok(fullSubmission).build();
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
    public Response getSubmissionPart(
            @PathParam("id") String fullSubmissionId, @PathParam("category") String category) {
        // get submission part from database based by id
        SubmissionPart submissionPart =
                submissionController.getSubmissionPart(fullSubmissionId, category.toUpperCase());

        if (submissionPart != null) {
            return Response.ok(submissionPart).build();
        } else {
            // declare response
            SubmissionResponse response = new SubmissionResponse();
            response.setMessage(
                    "Submission part with the full submission id '" + fullSubmissionId + "' and the category '" + category
                            .toUpperCase() + "' can't be found");

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
        ArrayList<SubmissionProjectRepresentation> representations =
                submissionController.getSubmissionPartsByProjectId(projectName);

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
    public void finalize(
            @PathParam("submissionId") String submissionId, @PathParam("projectId") String projectId,
            @Context HttpServletRequest req) throws Exception {
        String userEmail = (String) req.getSession().getAttribute(GFContexts.USEREMAIL);
        User user = userDAO.getUserByEmail(userEmail);
        Project project = projectDAO.getProjectByName(projectId);
        FullSubmission fullSubmission = submissionController.getFullSubmission(submissionId);
        dossierCreationProcess.finalizeDossier(fullSubmission, project, user);
    }

    @GET
    @Path("categories/project/{projectName}")
    public List<String> getAnnotationCategories(@PathParam("projectName") String projectName) {
        List<String> result = submissionController.getAnnotationCategories(new Project(projectName));
        if (result.size() == 0) {
            return Categories.standardAnnotationCategories;
        }
        return result;
    }

    @GET
    @Path("/visibilities/personal/{personal}")
    public Response getPossibleVisibilities(@PathParam("personal") boolean personal) {
        HashMap<Visibility, String> visibilityButtonTextHashMap = Visibility.toVisibilityButtonTextMap();
        if (!personal) {
            visibilityButtonTextHashMap.remove(Visibility.PERSONAL);
        }
        return Response.ok(visibilityButtonTextHashMap).build();
    }

    @GET
    @Path("portfolio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPortfolioEntries(
            @Context HttpServletRequest req, @QueryParam("projectName") String projectName,
            @QueryParam("visibility") Visibility visibility) {
        String userEmail;
        try {
            userEmail = gfContexts.getUserEmail(req);
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("userEmail not found in context")
                    .build();
        }
        Project project = new Project(projectName);
        User user = userDAO.getUserByEmail(userEmail);
        Integer groupId = groupDAO.getMyGroupId(user, project);

        List<FullSubmission> fullSubmissionList = new ArrayList<>();

        switch (visibility) {
            case DOCENT:
                if (user.getStudent()) {
                    fullSubmissionList =
                            submissionController.getDocentViewableSubmissions(user, project, FileRole.PORTFOLIO);
                } else {
                    fullSubmissionList =
                            submissionController.getProjectSubmissions(project, FileRole.PORTFOLIO, visibility);
                }
                break;
            case PUBLIC:
                fullSubmissionList =
                        submissionController.getProjectSubmissions(project, FileRole.PORTFOLIO, visibility);
                break;
            case GROUP:
                fullSubmissionList =
                        submissionController.getGroupSubmissions(project, groupId, FileRole.PORTFOLIO, visibility);
                break;
            case PERSONAL:
                fullSubmissionList = submissionController.getPersonalSubmissions(user, project, FileRole.PORTFOLIO);
        }

        if (fullSubmissionList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No portfolio entries found").build();
        }
        return Response.ok(fullSubmissionList).build();
    }

    @PUT
    @Path("portfolio/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePortfolioEntry(
            @Context HttpServletRequest request, @PathParam("id") String id,
            FullSubmissionPostRequest fullSubmissionPostRequest) {
        if (fullSubmissionPostRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("fullSubmissionPostRequest is null").build();
        }
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("id is null").build();
        }
        submissionController.updateFullSubmissionTextAndVisibility(fullSubmissionPostRequest);

        return Response.ok().build();
    }
}
