package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.ProjectDescription;

import java.util.ArrayList;

/**
 * Service for learning Journal
 */

public interface ProjectDescriptionService {


    ProjectDescription getProjectbyStudent(StudentIdentifier studentIdentifier);

    ProjectDescription getProjectbyId(String id);
    void saveProjectText(StudentIdentifier studentIdentifier, String text);

    void addLink(String project, String link, String name);

    void deleteLink(String link);

    void closeDescription(String projectDescriptionId);

    boolean checkIfAllDescriptionsClosed(Project project);

    ArrayList<User> getOpenUserByProject(Project project);
}
