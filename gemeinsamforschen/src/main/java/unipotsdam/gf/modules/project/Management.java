package unipotsdam.gf.modules.project;

import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserInterests;

import java.io.FileInputStream;
import java.util.List;

/**
 * Created by dehne on 31.05.2018.
 */
public interface Management {
    /**
     * delete a User in the database
     *
     * @param user who will be deleted
     */
    void delete(User user);

    /**
     * create a User in the database
     *
     * @param user that is about to be created
     */
    void create(User user);

    /**
     * create a Project in the database
     *
     * @param project that is about to be created
     */
    void create(Project project);

    void create(Project project, Integer groupSize);
    /**
     * create a Group in the database
     *
     * @param group a collection of students in a project, identified by a id
     */
    void create(Group group);

    /**
     * Delete a Project in the database
     *
     * @param project that is about to be deleted
     */
    void delete(Project project);

    /**
     * Update a User in the database
     *
     * @param user who's about to get an updated rocketChatName
     */
    void update(User user);

    void update(Group group);

    /**
     * Add an entry in the M:N table linking users and projects
     *
     * @param user who logs into a project
     * @param project a project which gets another participant
     * @param interests unused and empty todo: delete
     */
    void register(User user, Project project, UserInterests interests);

    /**
     * Check if a user exists in the DB
     *
     * @param user of interest
     * @return true if user exists in db
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

    List<Project> getProjects(String userEmail);

    List<Project> getProjectsStudent(User user);

    List<Project> getJustStartedProjects();

    String saveProfilePicture(FileInputStream fis, String userName);

    List<String> getTags(Project project);

    Project getProjectByName(String projectName);


}

