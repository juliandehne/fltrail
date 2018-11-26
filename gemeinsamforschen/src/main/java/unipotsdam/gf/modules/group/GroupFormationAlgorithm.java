package unipotsdam.gf.modules.group;

import unipotsdam.gf.modules.project.Project;

public interface GroupFormationAlgorithm {
    java.util.List<Group> calculateGroups(Project project);
    int getMinNumberOfStudentsNeeded();
}
