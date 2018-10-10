package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.constraints.ConstraintsMessages;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.ProjectDescription;

import java.util.ArrayList;
import java.util.Map;

/**
 * Service for learning Journal
 */

public interface ProjectDescriptionService {


    ProjectDescription getProjectByStudent(StudentIdentifier userNameentifier);

    ProjectDescription getProjectById(String id);

    void saveProjectText(StudentIdentifier userNameentifier, String text);

    void addLink(String project, String link, String name);

    void deleteLink(String link);

    void closeDescription(String projectDescriptionId);

    Map<StudentIdentifier, ConstraintsMessages> checkIfAllDescriptionsClosed(Project project);

    ArrayList<User> getOpenUserByProject(Project project);
}
