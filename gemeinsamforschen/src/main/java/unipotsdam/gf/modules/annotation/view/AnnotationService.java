package unipotsdam.gf.modules.annotation.view;

import io.dropwizard.jersey.PATCH;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.annotation.controller.AnnotationController;
import unipotsdam.gf.modules.annotation.model.*;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskName;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
@Path("/annotations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnnotationService {


    @Inject
    AnnotationController controller;

    @Inject
    private IGroupFinding groupFinding;

    @Inject
    private GFContexts gfContexts;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private DossierCreationProcess dossierCreationProcess;

    @POST
    public Response createAnnotation(AnnotationPostRequest request) {

        // save annotation request in database and receive the new annotation object

        Annotation annotation = controller.addAnnotation(request);

        return Response.ok(annotation).build();

    }

    @PATCH
    @Path("{id}")
    public Response alterAnnotation(@PathParam("id") String annotationId, AnnotationPatchRequest request) {

        // declare response
        AnnotationResponse response = new AnnotationResponse();

        // check if annotation exists
        boolean exists = controller.existsAnnotationId(annotationId);

        if (exists) {
            // alter annotation and response 200
            controller.alterAnnotation(annotationId, request);
            response.setMessage("Altered the annotation with the id " + annotationId);

            return Response.ok(response).build();
        }
        else {
            // no annotation with the given id, response 404
            response.setMessage("Annotation with the id '" + annotationId + "' can't be found");

            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

    }

    @DELETE
    @Path("{id}")
    public Response deleteAnnotation(@PathParam("id") String annotationId) {

        // declare response
        AnnotationResponse response = new AnnotationResponse();

        // check if annotation exists
        boolean exists = controller.existsAnnotationId(annotationId);

        if (exists) {
            // delete annotation and response 200
            controller.deleteAnnotation(annotationId);
            response.setMessage("Deleted the annotation with the id " + annotationId);

            return Response.ok(response).build();
        }
        else {
            // no annotation with the given id, response 404
            response.setMessage("Annotation with the id '" + annotationId + "' can't be found");

            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

    }

    @GET
    @Path("{id}")
    public Response getAnnotation(@PathParam("id") String annotationId) {

        // receive the annotation
        Annotation annotation = controller.getAnnotation(annotationId);

        if (annotation != null) {
            return Response.ok(annotation).build();
        }
        else {
            // declare response
            AnnotationResponse response = new AnnotationResponse();
            response.setMessage("Annotation with the id '" + annotationId + "' can't be found");

            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

    }

    @GET
    @Path("/targetid/{id}/targetcategory/{category}")
    public Response getAnnotations(@PathParam("id") String targetId, @PathParam("category") String category) {

        // receive the annotation
        ArrayList<Annotation> annotations = controller.getAnnotations(targetId, Category.valueOf(category.toUpperCase()));

        if (!annotations.isEmpty()) {
            return Response.ok(annotations).build();
        }
        else {
            // declare response
            AnnotationResponse response = new AnnotationResponse();
            response.setMessage("Found no annotations for the target id '" + targetId + "'");

            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
    }


    @GET
    @Path("/finalize/projectName/{projectName}")
    @Produces("application/json")
    public String finalizeFeedback(@Context HttpServletRequest req, @PathParam("projectName") String projectName)
            throws IOException {
        Task task= new Task();
        String userEmail = gfContexts.getUserEmail(req);
        task.setProjectName(projectName);
        task.setUserEmail(userEmail);
        task.setTaskName(TaskName.GIVE_FEEDBACK);
        task.setProgress(Progress.FINISHED);
        Integer groupId = groupFinding.getMyGroupId(new User(userEmail), new Project(projectName));
        task.setGroupTask(groupId);
        controller.endFeedback(task);
        Project project = projectDAO.getProjectByName(projectName);
        dossierCreationProcess.createSeeFeedBackTask(project, groupId);
        dossierCreationProcess.createCloseFeedBackPhaseTask(project);
        return null;
    }

    @GET
    @Path("/feedbackTarget/projectName/{projectName}")
    public String getFeedBackTarget(@Context HttpServletRequest req, @PathParam("projectName") String projectName) throws IOException {
        String userEmail = gfContexts.getUserEmail(req);
        User user = userDAO.getUserByEmail(userEmail);
        Project project = projectDAO.getProjectByName(projectName);
        return dossierCreationProcess.getFeedBackTarget(project, user);
    }
}
