package unipotsdam.gf.modules.journal.model.dao;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.ProjectDescription;

import java.util.ArrayList;

public interface ProjectDescriptionDAO {

    void createDescription(ProjectDescription projectDescription);
    void updateDescription(ProjectDescription projectDescription);
    ProjectDescription getDescription(StudentIdentifier projectDescription);

    ProjectDescription getDescription(String id);
    void deleteDescription(StudentIdentifier projectDescription);
    void closeDescription(String projectDescriptionId);

    ArrayList<String> getOpenDescriptions(Project project);
}
