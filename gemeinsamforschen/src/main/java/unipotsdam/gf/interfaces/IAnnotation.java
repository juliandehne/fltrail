package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.annotation.model.Annotation;
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
     * @param annotationPostRequest The new annotation as an Object
     * @return Returns the new annotation
     */
    Annotation addAnnotation(AnnotationPostRequest annotationPostRequest);

    /**
     * Alters an annotation
     *
     * @param annotationId The id of the original annotation
     * @param newBody The new body of the annotation
     */
    void alterAnnotation(String annotationId, String newBody);

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
     * @param targetIds An ArrayList of target ids
     * @return Returns all annotations
     */
    ArrayList<Annotation> getAnnotations(ArrayList<Integer> targetIds);

    /**
     * Checks if an annotation id already exists in the database
     *
     * @param annotationId The id of the annotation
     * @return Returns true if the id exists
     */
    boolean existsAnnotationId(String annotationId);

}
