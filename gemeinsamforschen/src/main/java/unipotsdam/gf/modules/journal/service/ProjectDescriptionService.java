package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.modules.journal.model.ProjectDescription;

/**
 * Service for learning Journal
 */

public interface ProjectDescriptionService {


    ProjectDescription getProject(String project);

    void saveProjectText(String text);

    void addLink(String link, String name);

    void deleteLink(String link);

    void closeDescription(String desc);
}
