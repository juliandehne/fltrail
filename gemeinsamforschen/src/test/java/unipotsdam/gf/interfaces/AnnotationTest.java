package unipotsdam.gf.interfaces;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.modules.annotation.controller.AnnotationController;
import unipotsdam.gf.modules.annotation.model.Annotation;
import unipotsdam.gf.modules.annotation.model.AnnotationBody;
import unipotsdam.gf.modules.annotation.model.AnnotationPatchRequest;
import unipotsdam.gf.modules.annotation.model.AnnotationPostRequest;
import unipotsdam.gf.modules.annotation.model.Category;

import javax.inject.Inject;
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

    @Inject
    private AnnotationController controller;

    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        locator.inject(this);

    }

    @Test
    public void testAddAnnotation() {

        // initialize title and comment of body
        String title = "title_testAddAnnotation";
        String comment = "comment_testAddAnnotation";

        // prepare and execute request
        AnnotationPostRequest annotationPostRequest = new AnnotationPostRequest("userEmail", "1", Category.TITEL, new AnnotationBody(title, comment, 1, 2));
        Annotation response = controller.addAnnotation(annotationPostRequest);

        // the new annotation should be found in the database
        assertTrue("Can't find annotation with the id " + response.getId(), controller.existsAnnotationId(response.getId()));

        // delete the new annotation
        controller.deleteAnnotation(response.getId());

    }

    @Test
    public void testAlterAnnotation() {

        // initialize old and new title and comment of body
        String titleOld = "titleOld_testAlterAnnotation";
        String commentOld = "commentOld_testAlterAnnotation";
        String titleNew = "titleNew_testAlterAnnotation";
        String commentNew = "commentNew_testAlterAnnotation";

        // save new annotation in database
        AnnotationPostRequest annotationPostRequest = new AnnotationPostRequest("userEmail", "0", Category.TITEL, new AnnotationBody(titleOld, commentOld, 1, 2));
        Annotation response = controller.addAnnotation(annotationPostRequest);

        // the new annotation should be found in the database
        assertTrue("Can't find annotation with the id " + response.getId(), controller.existsAnnotationId(response.getId()));

        // the annotation's title should be "titleOld_testAlterAnnotation"
        assertEquals("The title of the annotation should be " + titleOld + " but was " + response.getBody().getTitle(), titleOld, response.getBody().getTitle());

        // the annotation's comment should be "commentOld_testAlterAnnotation"
        assertEquals("The comment of the annotation should be " + commentOld + " but was " + response.getBody().getComment(), commentOld, response.getBody().getComment());

        // alter the annotation and updateForUser the database
        AnnotationPatchRequest annotationPatchRequest = new AnnotationPatchRequest(titleNew, commentNew);
        controller.alterAnnotation(response.getId(), annotationPatchRequest);

        // receive the new annotation
        Annotation newResponse = controller.getAnnotation(response.getId());

        // the annotation's title should be "titleNew_testAlterAnnotation"
        assertEquals("The title of the annotation should be " + titleNew + " but was " + newResponse.getBody().getTitle(), titleNew, newResponse.getBody().getTitle());

        // the annotation's comment should be "commentNew_testAlterAnnotation"
        assertEquals("The comment of the annotation should be " + commentNew + " but was " + newResponse.getBody().getComment(), commentNew, newResponse.getBody().getComment());

        // delete the annotation
        controller.deleteAnnotation(response.getId());

    }

    @Test
    public void testDeleteAnnotation() {

        // initialize title and comment of body
        String title = "title_testDeleteAnnotation";
        String comment = "comment_testDeleteAnnotation";

        // prepare and execute request
        AnnotationPostRequest annotationPostRequest = new AnnotationPostRequest("userEmail", "1", Category.TITEL, new AnnotationBody(title, comment, 1, 2));
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

        // initialize title and comment of body
        String title = "title_testGetAnnotation";
        String comment = "comment_testGetAnnotation";

        // prepare and execute request
        AnnotationPostRequest annotationPostRequest = new AnnotationPostRequest("userEmail", "1", Category.TITEL, new AnnotationBody(title, comment, 1, 2));
        Annotation response = controller.addAnnotation(annotationPostRequest);

        // receive the new annotation
        Annotation getResponse = controller.getAnnotation(response.getId());

        // the annotation's title should be "title_testAlterAnnotation"
        assertEquals("The title of the annotation should be " + title + " but was " + response.getBody().getTitle(), title, response.getBody().getTitle());

        // the annotation's comment should be "comment_testAlterAnnotation"
        assertEquals("The comment of the annotation should be " + comment + " but was " + response.getBody().getComment(), comment, response.getBody().getComment());

        // delete the new annotation
        controller.deleteAnnotation(response.getId());

    }

    @Test
    public void testGetAnnotations() {

        // initialize title and comment of bodys
        String title = "title_testGetAnnotations";
        String comment = "comment_testGetAnnotations";

        // initialize targetIds
        ArrayList<String> targetIds = new ArrayList<>();
        targetIds.add("-1");
        targetIds.add("-2");

        // save new annotations in database
        AnnotationPostRequest request1 = new AnnotationPostRequest("userEmail", targetIds.get(0), Category.TITEL, new AnnotationBody(title, comment, 1, 2));
        AnnotationPostRequest request2 = new AnnotationPostRequest("userEmail", targetIds.get(1), Category.TITEL, new AnnotationBody(title, comment, 1, 2));
        AnnotationPostRequest request3 = new AnnotationPostRequest("userEmail", targetIds.get(1), Category.TITEL, new AnnotationBody(title, comment, 1, 2));
        controller.addAnnotation(request1);
        controller.addAnnotation(request2);
        controller.addAnnotation(request3);

        // receive the new annotations with targetId = -2
        ArrayList<Annotation> getResponse = controller.getAnnotations(targetIds.get(1), Category.TITEL);

        // the size of the getResponse should be 2
        assertEquals("The size of the response should be 2 but was " + getResponse.size(), 2, getResponse.size());

        // receive the new annotations with targetId = -1
        ArrayList<Annotation> getResponseNew = controller.getAnnotations(targetIds.get(0), Category.TITEL);

        // the size of the getResponse should be 1
        assertEquals("The size of the response should be 1 but was " + getResponseNew.size(), 1, getResponseNew.size());

        // delete annotations from database
        controller.deleteAnnotation(getResponse.get(0).getId());
        controller.deleteAnnotation(getResponse.get(1).getId());
        controller.deleteAnnotation(getResponseNew.get(0).getId());

    }

    @Test
    public void testExistsAnnotationId() {

        // initialize title and comment of body and bad id
        String title = "title_testExistsAnnotationId";
        String comment = "comment_testExistsAnnotationId";
        String badId = "badId";

        // save new annotation in database
        AnnotationPostRequest annotationPostRequest = new AnnotationPostRequest("userEmail", "0", Category.TITEL, new AnnotationBody(title, comment, 1, 2));
        Annotation response = controller.addAnnotation(annotationPostRequest);

        // the annotation shouldn't be found in the database
        assertFalse("There shouldn't be an annotation with the id " + badId, controller.existsAnnotationId(badId));

        // the new annotation should be found in the database
        assertTrue("There should be an annotation with the id " + response.getId(), controller.existsAnnotationId(response.getId()));

        // delete annotation from database
        controller.deleteAnnotation(response.getId());

    }

}
