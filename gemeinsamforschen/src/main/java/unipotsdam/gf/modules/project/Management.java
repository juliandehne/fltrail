package unipotsdam.gf.modules.project;

import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectConfiguration;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserInterests;
import unipotsdam.gf.modules.user.UserProfile;

import java.io.FileInputStream;
import java.util.List;

/**
 * Created by dehne on 31.05.2018.
 * TODO in DAOs refaktorisieren, sobald es mehr wird
 */
public interface Management {
    /**
     * delete a User in the database
     *
     * @param user
     */
    void delete(User user);

    /**
     * create a User in the database
     *
     * @param user
     * @param profile
     */
    void create(User user, UserProfile profile);

    /**
     * create a Project in the database
     *
     * @param project
     */
    void create(Project project);

    /**
     * create a Group in the database
     *
     * @param group
     */
    void create(Group group);

    /**
     * Delete a Project in the database
     *
     * @param project
     */
    void delete(Project project);

    /**
     * Update a User in the database
     *
     * @param user
     */
    void update(User user);

    void update(Group group);

    /**
     * Add an entry in the M:N table linking users and projects
     *
     * @param user
     * @param project
     * @param interests
     */
    void register(User user, Project project, UserInterests interests);

    /**
     * Check if a user exists in the DB
     *
     * @param user
     * @return
     */
    Boolean exists(User user);

    /**
     * Check if a project exists in the DB
     */

    Boolean exists(Project project);

    Boolean exists(Group group);

    User getUserByEmail(String userEmail);

    void create(ProjectConfiguration projectConfiguration, Project project);

    ProjectConfiguration getProjectConfiguration(Project project);

    List<String> getProjects(String userEmail);

    List<Project> getProjectsStudent(String studentToken);

    List<Project> getAllProjects();

    String saveProfilePicture(FileInputStream fis, String userName);

    List<String> getTags(Project project);

    Project getProjectByName(String projectName);


}

