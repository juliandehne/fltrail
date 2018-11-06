package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.GroupfindingCriteria;

import java.util.ArrayList;
import java.util.List;

public interface IGroupFinding {

    /**
     * Select the groupfinding criteria used
     * @param groupfindingCriteria
     * @param project
     */
    void selectGroupfindingCriteria(
            GroupfindingCriteria groupfindingCriteria, Project project);

    /**
     * Persist the selected manual groups
     * @param groupComposition
     */
    void persistGroups(java.util.List<Group> groupComposition, Project project);

    /**
     * @param project
     * @return
     */
    java.util.List<Group> getGroups(Project project);

    /**
     *
     * @param groupFindingMechanism
     */
    void formGroups(GroupFormationMechanism groupFindingMechanism);


    /**
     *
     * @param student
     * @return
     */
    ArrayList<String> getStudentsInSameGroup(StudentIdentifier student);

    int getMinNumberOfStudentsNeeded(Project project);

    List<Group> createRandomGroups(Project project);
}
