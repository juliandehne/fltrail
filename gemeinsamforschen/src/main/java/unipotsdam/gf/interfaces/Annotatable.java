package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.annotation.Annotation;

import java.util.ArrayList;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public interface Annotatable {

    /**
     * Adds an annotation to a target and returns the new id
     *
     * @param newAnnotation The new annotation as an Object
     * @return Returns the id of the new annotation
     */
    int addAnnotation(Annotation newAnnotation);

    /**
     * Alters an annotation
     *
     * @param annotationId The id of the original annotation
     * @param newBody The new body of the annotation
     */
    void alterAnnotation(int annotationId, String newBody);

    /**
     * Deletes an annotation
     *
     * @param annotationId The id of the annotation
     */
    void deleteAnnotation(int annotationId);

    /**
     * Returns a specific annotation from a target
     *
     * @param annotationId The id of the annotation
     * @param targetId The id of the target
     * @return Returns a specific annotation
     */
    Annotation getAnnotation(int annotationId, int targetId);

    /**
     * Returns all annotations from a target
     *
     * @param targetIds An ArrayList of target ids
     * @return Returns all annotations
     */
    ArrayList<Annotation> getAnnotations(ArrayList<Integer> targetIds);

}
