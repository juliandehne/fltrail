package unipotsdam.gf.modules.group.preferences;

import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupFormationAlgorithm;
import unipotsdam.gf.modules.project.Project;

import java.util.List;

public class UserPreferenceAlgorithm implements GroupFormationAlgorithm {
    @Override
    public List<Group> calculateGroups(Project project) {
        return null;
    }

    @Override
    public int getMinNumberOfStudentsNeeded() {
        return 0;
    }
}
