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
    public void selectGroupfindingCriteria(
            GroupfindingCriteria groupfindingCriteria, Project project) {
        //
    }

    @Override
    public void persistGroups(List<Group> groupComposition, Project project) {
        for (Group group : groupComposition) {
            group.setProjectName(project.getName());
            groupDAO.persist(group);
        }
    }


    @Override
    public List<Group> getGroups(Project project) {
        return groupDAO.getGroupsByProjectName(project.getName());
    }

    @Override
    public void formGroups(GroupFormationMechanism groupFindingMechanism) {
        // TODO implement for othermechanisms
    }

    public ArrayList<String> getStudentsInSameGroup(StudentIdentifier student) {
        return groupDAO.getStudentsInSameGroupAs(student);
    }
}
