package unipotsdam.gf.interfaces;

/**
 * @author Sven Kästle
 * skaestle@uni-potsdam.de
 */
public interface Annotatable {

    /**
     * Adds an annotation to a document and returns the new id
     *
     * @param userId The id of the author of the annotation
     * @param annotation The annotation as an Object
     * @return Returns the id of the new annotation
     */
    String addAnnotation(String userId, Object annotation);

    /**
     * Alters an annotation
     *
     * @param id The id of the annotation
     * @param annotation The annotation as an Object
     */
    void alterAnnotation(String id, Object annotation);

    /**
     * Deletes an annotation
     *
     * @param id The id of the annotation
     */
    void deleteAnnotation(String id);

    /**
     * Returns a specific annotation from a document
     *
     * @param id The id of the annotation
     * @return Returns a specific annotation
     */
    Object getAnnotation(String id);

    /**
     * Return all annotations from a document
     *
     * @return Returns all annotations
     */
    Object[] getAnnotations();

}
