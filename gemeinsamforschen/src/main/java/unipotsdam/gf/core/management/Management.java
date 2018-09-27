package unipotsdam.gf.core.management;

import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.project.ProjectConfiguration;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserInterests;
import unipotsdam.gf.core.management.user.UserProfile;

import javax.ws.rs.Produces;
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
    String create(Project project);

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

    User getUserByName(String userName);

    void create(ProjectConfiguration projectConfiguration, Project project);

    ProjectConfiguration getProjectConfiguration(Project project);

    String getProjectToken(String projectName, String password);

    Project getProjectByToken(String projectToken);

    List<String> getProjects(String userEmail);

    User getUserByToken(String userEmail);

    Project getProjectById(String projectName);

    List<String> getProjectsStudent(String studentToken);

    String saveProfilePicture(FileInputStream fis, String userName);

    List<String> getTags(Project project);
}

