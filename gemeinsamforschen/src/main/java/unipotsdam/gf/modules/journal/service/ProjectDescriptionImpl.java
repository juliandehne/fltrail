package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Link;
import unipotsdam.gf.modules.journal.model.ProjectDescription;
import unipotsdam.gf.modules.journal.model.dao.LinkDAO;
import unipotsdam.gf.modules.journal.model.dao.LinkDAOImpl;
import unipotsdam.gf.modules.journal.model.dao.ProjectDescriptionDAO;
import unipotsdam.gf.modules.journal.model.dao.ProjectDescriptionDAOImpl;

import java.util.ArrayList;

public class ProjectDescriptionImpl implements ProjectDescriptionService {

    private final ProjectDescriptionDAO descriptionDAO = new ProjectDescriptionDAOImpl();
    private final LinkDAO linkDAO = new LinkDAOImpl();

    @Override
    public ProjectDescription getProjectByStudent(StudentIdentifier studentIdentifier) {

        //if no description exists (when page is opened for the first time), create a new one
        if(descriptionDAO.getDescription(studentIdentifier)==null){
            ProjectDescription description = new ProjectDescription("0", studentIdentifier.getStudentId(), "Hier soll ein Turtorialtext stehen", studentIdentifier.getProjectId(), null);
            descriptionDAO.createDescription(description);
        }

        ProjectDescription returnDesc = descriptionDAO.getDescription(studentIdentifier);
        returnDesc.setLinks(linkDAO.getAllLinks(returnDesc.getId()));
        return returnDesc;

    }

    @Override
    public ProjectDescription getProjectById(String id) {
        ProjectDescription returnDesc = descriptionDAO.getDescription(id);
        returnDesc.setLinks(linkDAO.getAllLinks(returnDesc.getId()));
        return returnDesc;
    }

    @Override
    public void saveProjectText(StudentIdentifier studentIdentifier, String text) {

        ProjectDescription desc = getProjectByStudent(studentIdentifier);
        desc.setDescription(text);
        descriptionDAO.updateDescription(desc);
    }

    @Override
    public void addLink(String project, String link, String name) {
        Link newLink = new Link(project,project,name,link);
        linkDAO.addLink(newLink);
    }

    @Override
    public void deleteLink(String link) {
        linkDAO.deleteLink(link);
    }

    @Override
    public void closeDescription(String projectDescrID) {
        descriptionDAO.closeDescription(projectDescrID);
    }

    @Override
    public boolean checkIfAllDescriptionsClosed(Project project) {
        return (descriptionDAO.getOpenDescriptions(project).size() == 0);
    }

    @Override
    public ArrayList<User> getOpenUserByProject(Project project) {
        ManagementImpl management = new ManagementImpl();

        ArrayList<String> userId = descriptionDAO.getOpenDescriptions(project);
        ArrayList<User> users = new ArrayList<>();

        for(String id : userId){
            users.add(management.getUserByToken(id));
        }
        return users;
    }
}
