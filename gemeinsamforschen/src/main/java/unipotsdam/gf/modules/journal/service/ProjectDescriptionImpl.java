package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.modules.journal.model.*;

public class ProjectDescriptionImpl implements ProjectDescriptionService {

    ProjectDescriptionDAO descriptionDAO = new ProjectDescriptionDAOImpl();
    LinkDAO linkDAO = new LinkDAOImpl();

    @Override
    public ProjectDescription getProject(String project) {

        return descriptionDAO.getDescription(project);
    }

    @Override
    public void saveProjectText(String project, String text) {

        ProjectDescription desc = getProject(project);
        desc.setDescription(text);
        descriptionDAO.updateDescription(desc);
    }

    @Override
    public void addLink(String project, String link, String name) {
        Link newLink = new Link("0",project,name,link);
        linkDAO.addLink(newLink);
    }

    @Override
    public void deleteLink(String link) {
        linkDAO.deleteLink(link);
    }

    @Override
    public void closeDescription(String desc) {
        descriptionDAO.closeDescription(desc);
    }
}
