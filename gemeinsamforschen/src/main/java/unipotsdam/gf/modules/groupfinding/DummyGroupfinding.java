package unipotsdam.gf.modules.groupfinding;

import unipotsdam.gf.core.management.group.Group;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.assignments.Assignee;
import unipotsdam.gf.assignments.NotImplementedLogger;
import unipotsdam.gf.interfaces.IGroupFinding;

import java.util.ArrayList;
import java.util.List;

public class DummyGroupfinding implements IGroupFinding {
    @Override
    public void selectGroupfindingCriteria(GroupfindingCriteria groupfindingCriteria)  {
        NotImplementedLogger.logAssignment(Assignee.MIRJAM, IGroupFinding.class);
    }

    @Override
    public void persistGroups(
            List<Group> groupComposition, Project project){
        NotImplementedLogger.logAssignment(Assignee.MIRJAM, IGroupFinding.class);
    }

    @Override
    public List<Group> getGroups(Project project) {
        NotImplementedLogger.logAssignment(Assignee.MIRJAM, IGroupFinding.class);
        return new ArrayList<>();
    }

    @Override
    public void formGroups(GroupFormationMechanism groupFindingMechanism) {
        NotImplementedLogger.logAssignment(Assignee.MIRJAM, IGroupFinding.class);
    }
}
