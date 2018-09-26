package unipotsdam.gf.modules.annotation.view;

import io.dropwizard.jersey.PATCH;
import unipotsdam.gf.modules.annotation.controller.AnnotationController;
import unipotsdam.gf.modules.annotation.model.Annotation;
import unipotsdam.gf.modules.annotation.model.AnnotationPatchRequest;
import unipotsdam.gf.modules.annotation.model.AnnotationPostRequest;
import unipotsdam.gf.modules.annotation.model.AnnotationResponse;
import unipotsdam.gf.modules.peer2peerfeedback.Category;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
@Path("/annotations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnnotationService {

    @POST
    public Response createAnnotation(AnnotationPostRequest request) {

        // save annotation request in database and receive the new annotation object
        AnnotationController controller = new AnnotationController();
        Annotation annotation = controller.addAnnotation(request);

        return Response.ok(annotation).build();

    }

    @PATCH
    @Path("{id}")
    public Response alterAnnotation(@PathParam("id") String annotationId, AnnotationPatchRequest request) {

        // declare response
        AnnotationResponse response = new AnnotationResponse();

        // check if annotation exists
        AnnotationController controller = new AnnotationController();
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
        AnnotationController controller = new AnnotationController();
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
        AnnotationController controller = new AnnotationController();
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
        AnnotationController controller = new AnnotationController();
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

}
