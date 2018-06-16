package unipotsdam.gf.modules.annotation.view;

import com.google.gson.Gson;
import io.dropwizard.jersey.PATCH;
import unipotsdam.gf.modules.annotation.controller.AnnotationController;
import unipotsdam.gf.modules.annotation.model.Annotation;
import unipotsdam.gf.modules.annotation.model.AnnotationPatchRequest;
import unipotsdam.gf.modules.annotation.model.AnnotationPostRequest;

import javax.ws.rs.*;
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

        // build response
        Gson gson = new Gson();
        String response = gson.toJson(annotation);

        return Response.status(201).entity(response).build();

    }

    @PATCH
    @Path("{id}")
    public Response alterAnnotation(@PathParam("id") String annotationId, AnnotationPatchRequest request) {

        // declare response and initialize gson
        String response;
        Gson gson = new Gson();

        // check if annotation exists
        AnnotationController controller = new AnnotationController();
        boolean exists = controller.existsAnnotationId(annotationId);

        if (exists) {
            // alter annotation and response 200
            controller.alterAnnotation(annotationId, request);
            response = gson.toJson("Altered the annotation with the id " + annotationId);

            return Response.status(200).entity(response).build();
        }
        else {
            // no annotation with the given id, response 404
            response = gson.toJson("Annotation with the id '" + annotationId + "' can't be found");

            return Response.status(404).entity(response).build();
        }

    }

    @DELETE
    @Path("{id}")
    public Response deleteAnnotation(@PathParam("id") String annotationId) {

        // declare response and initialize gson
        String response;
        Gson gson = new Gson();

        // check if annotation exists
        AnnotationController controller = new AnnotationController();
        boolean exists = controller.existsAnnotationId(annotationId);

        if (exists) {
            // delete annotation and response 200
            controller.deleteAnnotation(annotationId);

            response = gson.toJson("Deleted the annotation with the id " + annotationId);

            return Response.status(204).entity(response).build();

        }
        else {
            // no annotation with the given id, response 404
            response = gson.toJson("Annotation with the id '" + annotationId + "' can't be found");
            return Response.status(404).entity(response).build();
        }

    }

    @GET
    @Path("{id}")
    public Response getAnnotation(@PathParam("id") String annotationId) {

        // declare response and initialize gson
        String response;
        Gson gson = new Gson();

        // receive the annotation
        AnnotationController controller = new AnnotationController();
        Annotation annotation = controller.getAnnotation(annotationId);

        if (annotation != null) {
            response = gson.toJson(annotation);
            return Response.status(200).entity(response).build();
        }
        else {
            response = gson.toJson("Annotation with the id '" + annotationId + "' can't be found");
            return Response.status(404).entity(response).build();
        }

    }

    @GET
    @Path("/target/{id}")
    public Response getAnnotations(@PathParam("id") int targetId) {

        // declare response and initialize gson
        String response;
        Gson gson = new Gson();

        // receive the annotation
        AnnotationController controller = new AnnotationController();
        ArrayList<Annotation> annotations = controller.getAnnotations(targetId);

        if (!annotations.isEmpty()) {
            response = gson.toJson(annotations);
            return Response.status(200).entity(response).build();
        }
        else {
            response = gson.toJson("Found no annotations for the target id '" + targetId + "'");
            return Response.status(404).entity(response).build();
        }

    }

}
