package unipotsdam.gf.core.management;

import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserInterests;
import unipotsdam.gf.core.management.user.UserProfile;
import unipotsdam.gf.modules.assessment.controller.StudentIdentifier;

import java.util.List;

/**
 * Created by dehne on 31.05.2018.
 * TODO in DAOs refaktorisieren, sobald es mehr wird
 */
public interface Management {
    /**
     * delete a User in the database
     *
     * @param identifier
     */
    void delete(StudentIdentifier identifier);

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
     * Get all the users linked to a project
     *
     * @param project
     * @return
     */
    List<User> getUsers(Project project);

    /**
     * get the token for the user
     *
     * @param user
     * @return
     */
    String getUserToken(User user);

    /**
     * get the user given his http token
     *
     * @param token
     * @return
     */
    User getUser(String token);

    void createGroup(List<User> groupMembers, String projectId);

    void addGroupMember(User groupMember, int groupId);

    void deleteGroupMember(User groupMember, int groupId);

    List<Group> getGroups(String projectId);
}

