package unipotsdam.gf.modules.group;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GroupfindingImpl implements IGroupFinding {

    private GroupDAO groupDAO;

    @Inject
    public GroupfindingImpl(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public void selectGroupfindingCriteria(GroupfindingCriteria groupfindingCriteria) {

    }

    @Override
    public void persistGroups(List<Group> groupComposition, Project project) {

    }


    @Override
    public List<Group> getGroups(Project project) {
        return null;
    }

    @Override
    public void formGroups(GroupFormationMechanism groupFindingMechanism) {

    }

    public ArrayList<String> getStudentsInSameGroup(StudentIdentifier student) {
        return groupDAO.getStudentsInSameGroupAs(student);
    }
}
