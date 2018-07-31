package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.ProjectDescription;

/**
 * Service for learning Journal
 */

public interface ProjectDescriptionService {


    ProjectDescription getProject(StudentIdentifier studentIdentifier);

    void saveProjectText(StudentIdentifier studentIdentifier, String text);

    void addLink(String project, String link, String name);

    void deleteLink(String link);

    void closeDescription(String projectDescriptionId);
}
