package unipotsdam.gf.modules.groupfinding;

import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.groupfinding.service.GroupDAO;

import java.util.ArrayList;
import java.util.List;

public class GroupfindingImpl implements IGroupFinding {
    @Override
    public void selectGroupfindingCriteria(GroupfindingCriteria groupfindingCriteria) {

    }

    @Override
    public void persistGroups(
            List<Group> groupComposition, Project project) {

    }

    @Override
    public List<Group> getGroups(Project project) {
        return null;
    }

    @Override
    public void formGroups(GroupFormationMechanism groupFindingMechanism) {

    }

    public ArrayList<String> getStudentsInSameGroup(StudentIdentifier student) {
        return new GroupDAO().getStudentsInSameGroupAs(student);
    }
}
