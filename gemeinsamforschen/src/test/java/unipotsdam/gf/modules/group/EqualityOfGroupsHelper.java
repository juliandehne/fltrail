package unipotsdam.gf.modules.group;

import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;

import javax.inject.Inject;
import java.util.List;

public class EqualityOfGroupsHelper {

    @Inject
    GroupDAO groupDAO;
    public Boolean checkProject(String projectName) {
        //funktioniert noch nicht
        List<Group> originals = groupDAO.getOriginalGroupsByProjectName(projectName);
        List<Group> actuals = groupDAO.getGroupsByProjectName(projectName);

        return true;
    }
}
