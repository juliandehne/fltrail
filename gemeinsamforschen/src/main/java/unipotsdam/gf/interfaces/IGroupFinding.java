package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.groupfinding.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.groupfinding.GroupFormationMechanism;
import unipotsdam.gf.modules.groupfinding.GroupfindingCriteria;

import java.util.ArrayList;

public interface IGroupFinding {

    /**
     * Select the groupfinding criteria used
     * @param groupfindingCriteria
     */
    void selectGroupfindingCriteria(GroupfindingCriteria groupfindingCriteria);

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
}
