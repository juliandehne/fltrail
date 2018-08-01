package unipotsdam.gf.modules.journal.model.dao;

import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.ProjectDescription;

public interface ProjectDescriptionDAO {

    void createDescription(ProjectDescription projectDescription);
    void updateDescription(ProjectDescription projectDescription);
    ProjectDescription getDescription(StudentIdentifier projectDescription);

    ProjectDescription getDescription(String id);
    void deleteDescription(StudentIdentifier projectDescription);
    void closeDescription(String projectDescriptionId);
}
