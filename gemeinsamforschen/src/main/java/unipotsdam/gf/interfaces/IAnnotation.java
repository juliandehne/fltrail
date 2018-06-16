package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.annotation.model.Annotation;
import unipotsdam.gf.modules.annotation.model.AnnotationPatchRequest;
import unipotsdam.gf.modules.annotation.model.AnnotationPostRequest;

import java.util.ArrayList;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public interface IAnnotation {

    /**
     * Adds an annotation to a target and returns the new id
     *
     * @param annotationPostRequest The annotation post request
     * @return Returns the new annotation
     */
    Annotation addAnnotation(AnnotationPostRequest annotationPostRequest);

    /**
     * Alters an annotation
     *
     * @param annotationId The id of the original annotation
     * @param annotationPatchRequest The annotation patch request
     */
    void alterAnnotation(String annotationId, AnnotationPatchRequest annotationPatchRequest);

    /**
     * Deletes an annotation
     *
     * @param annotationId The id of the annotation
     */
    void deleteAnnotation(String annotationId);

    /**
     * Returns a specific annotation from a target
     *
     * @param annotationId The id of the annotation
     * @return Returns a specific annotation
     */
    Annotation getAnnotation(String annotationId);

    /**
     * Returns all annotations from a target
     *
     * @param targetId the target id
     * @return Returns all annotations
     */
    ArrayList<Annotation> getAnnotations(int targetId);

    /**
     * Checks if an annotation id already exists in the database
     *
     * @param annotationId The id of the annotation
     * @return Returns true if the id exists
     */
    boolean existsAnnotationId(String annotationId);

}
