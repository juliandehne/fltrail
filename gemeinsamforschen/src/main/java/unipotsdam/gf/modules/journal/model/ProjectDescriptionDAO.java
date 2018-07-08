package unipotsdam.gf.modules.journal.model;

public interface ProjectDescriptionDAO {

    void createDescription(ProjectDescription projectDescription);
    void updateDescription(ProjectDescription projectDescription);
    ProjectDescription getDescription(String projectDescription);
    void deleteDescription(String projectDescription);
    void closeDescription(String projectDescription);
}
