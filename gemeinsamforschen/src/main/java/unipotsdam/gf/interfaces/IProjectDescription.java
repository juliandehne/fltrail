package unipotsdam.gf.interfaces;

import java.util.ArrayList;

/**
 * Interface for Project Description
 */
public interface IProjectDescription {

    /**
     * Save description to database
     * @param description
     */
    void saveDescription(String description);

    /**
     * Add a new link to ProjectDescription
     * @param link url of the link
     * @param name name to shoe on website
     */
    void addLink(String link, String name);

    /**
     * Delete link
     * @param name name of the link
     */
    void deleteLink(String name);

    /**
     * Get name of the project
     * @param projectId Id of the project
     * @return name of the project
     */

    String getName(long projectId);

    /**
     * Get Description of the project
     * @param projectId Id of the project
     * @return Desription of the project
     */
    String getDescription(long projectId);

    /**
     * Get Lecturer of the project
     * @param projectId Id of the project
     * @return Lecturer of the project
     */
    long getLecturer(long projectId);

    /**
     * Get all Students of the project (Group)
     * @param projectId Id of the Project
     * @return Students of the project
     */
    ArrayList<Long> getStudents(long projectId);

    /**
     * Get all Links of the project
     * @param projectId Id of the Project
     * @return all links of the project
     */
    ArrayList<Long> getLinks(long projectId);



}
