package unipotsdam.gf.interfaces;

import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.modules.annotation.controller.AnnotationController;
import unipotsdam.gf.modules.annotation.model.Annotation;
import unipotsdam.gf.modules.annotation.model.AnnotationPostRequest;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public class AnnotationTest {

    // declare annotation controller
    AnnotationController controller;

    @Before
    public void initializeTest() {
        // initialize controller
        controller = new AnnotationController();
    }

    @Test
    public void testAddAnnotation() {

        // initialize body
        String body = "body_testAddAnnotation";

        // prepare and execute request
        AnnotationPostRequest annotationPostRequest = new AnnotationPostRequest(1, 2, body, 4, 5);
        Annotation response = controller.addAnnotation(annotationPostRequest);

        // the new annotation should be found in the database
        assertTrue("Can't find annotation with the id " + response.getId(), controller.existsAnnotationId(response.getId()));

        // delete the new annotation
        controller.deleteAnnotation(response.getId());

    }

    @Test
    public void testAlterAnnotation() {

        // initialize old and new body
        String oldBody = "bodyOld_testAlterAnnotation";
        String newBody = "bodyNew_testAlterAnnotation";

        // save new annotation in database
        AnnotationPostRequest annotationPostRequest = new AnnotationPostRequest(0, 0, oldBody, 0, 0);
        Annotation response = controller.addAnnotation(annotationPostRequest);

        // the new annotation should be found in the database
        assertTrue("Can't find annotation with the id " + response.getId(), controller.existsAnnotationId(response.getId()));

        // the annotation's body should be "testAlterAnnotation_oldBody"
        assertEquals("The body of the annotation should be " + oldBody + " but was " + response.getBody(), oldBody, response.getBody());

        // alter the annotation and update the database
        controller.alterAnnotation(response.getId(), newBody);

        // receive the new annotation
        Annotation newResponse = controller.getAnnotation(response.getId());

        // the annotation's body should be "testAlterAnnotation_newBody"
        assertEquals("The body of the annotation should be " + newBody + " but was " + newResponse.getBody(), newBody, newResponse.getBody());

        // delete the annotation
        controller.deleteAnnotation(response.getId());


    }

    @Test
    public void testDeleteAnnotation() {

        // initialize old and new body
        String body = "body_testDeleteAnnotation";

        // save new annotation in database
        AnnotationPostRequest annotationPostRequest = new AnnotationPostRequest(0, 0, body, 0, 0);
        Annotation response = controller.addAnnotation(annotationPostRequest);

        // the new annotation should be found in the database
        assertTrue("Can't find annotation with the id " + response.getId(), controller.existsAnnotationId(response.getId()));

        // delete the annotation
        controller.deleteAnnotation(response.getId());

        // the annotation shouldn't be found in the database
        assertFalse("There shouldn't be an annotation with the id " + response.getId(), controller.existsAnnotationId(response.getId()));

    }

    @Test
    public void testGetAnnotation() {

        // initialize body
        String body = "body_testGetAnnotation";

        // save new annotation in database
        AnnotationPostRequest annotationPostRequest = new AnnotationPostRequest(0, 0, body, 0, 0);
        Annotation response = controller.addAnnotation(annotationPostRequest);

        // receive the new annotation
        Annotation getResponse = controller.getAnnotation(response.getId());

        // the annotation's body should be "testAlterAnnotation_newBody"
        assertEquals("The body of the annotation should be " + body + " but was " + getResponse.getBody(), body, getResponse.getBody());

        // delete the new annotation
        controller.deleteAnnotation(response.getId());

    }

    @Test
    public void testGetAnnotations() {

        // initialize bodys
        String body1 = "body1_testGetAnnotations";
        String body2 = "body2_testGetAnnotations";

        // initialize targetIds
        ArrayList<Integer> targetIds = new ArrayList<>();
        targetIds.add(-1);
        targetIds.add(-2);

        // save new annotations in database
        AnnotationPostRequest request1 = new AnnotationPostRequest(0, targetIds.get(0), body1, 0, 0);
        AnnotationPostRequest request2 = new AnnotationPostRequest(0, targetIds.get(1), body2, 0, 0);
        controller.addAnnotation(request1);
        controller.addAnnotation(request2);

        // receive the new annotations
        ArrayList<Annotation> getResponse = controller.getAnnotations(targetIds);

        // the size of the  getResponse should be 2
        assertEquals("The size of the response should be 2 but was " + getResponse.size(), 2, getResponse.size());

        // initialize new targetIds
        ArrayList<Integer> targetIdsNew = new ArrayList<>();
        targetIdsNew.add(-1);

        // receive the new annotations
        ArrayList<Annotation> getResponseNew = controller.getAnnotations(targetIdsNew);

        // the size of the  getResponse should be 2
        assertEquals("The size of the response should be 1 but was " + getResponseNew.size(), 1, getResponseNew.size());

        // delete annotations from database
        controller.deleteAnnotation(getResponse.get(0).getId());
        controller.deleteAnnotation(getResponse.get(1).getId());

    }

    @Test
    public void testExistsAnnotationId() {

        // initialize body and bad id
        String body = "body_testExistsAnnotationId";
        String badId = "badId";

        // save new annotation in database
        AnnotationPostRequest annotationPostRequest = new AnnotationPostRequest(0, 0, body, 0, 0);
        Annotation response = controller.addAnnotation(annotationPostRequest);

        // the annotation shouldn't be found in the database
        assertFalse("There shouldn't be an annotation with the id " + badId, controller.existsAnnotationId(badId));

        // the new annotation should be found in the database
        assertTrue("There should be an annotation with the id " + response.getId(), controller.existsAnnotationId(response.getId()));

        // delete annotation from database
        controller.deleteAnnotation(response.getId());

    }

}
