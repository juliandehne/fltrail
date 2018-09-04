package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Link;
import unipotsdam.gf.modules.journal.model.ProjectDescription;
import unipotsdam.gf.modules.journal.model.dao.LinkDAO;
import unipotsdam.gf.modules.journal.model.dao.LinkDAOImpl;
import unipotsdam.gf.modules.journal.model.dao.ProjectDescriptionDAO;
import unipotsdam.gf.modules.journal.model.dao.ProjectDescriptionDAOImpl;

import java.util.Date;

public class ProjectDescriptionImpl implements ProjectDescriptionService {

    ProjectDescriptionDAO descriptionDAO = new ProjectDescriptionDAOImpl();
    LinkDAO linkDAO = new LinkDAOImpl();

    @Override
    public ProjectDescription getProjectbyStudent(StudentIdentifier studentIdentifier) {

        //if no description exists, create a new
        if(descriptionDAO.getDescription(studentIdentifier)==null){
            //TODO richtige Daten, standartwerte über config?
            ProjectDescription description = new ProjectDescription("0", studentIdentifier.getStudentId(), "Hier soll ein Turtorialtext stehen", studentIdentifier.getProjectId(), null, null, new Date().getTime());
            descriptionDAO.createDescription(description);
        }

        ProjectDescription returnDesc = descriptionDAO.getDescription(studentIdentifier);
        returnDesc.setLinks(linkDAO.getAllLinks(returnDesc.getId()));
        return returnDesc;

    }

    @Override
    public ProjectDescription getProjectbyId(String id) {
        ProjectDescription returnDesc = descriptionDAO.getDescription(id);
        returnDesc.setLinks(linkDAO.getAllLinks(returnDesc.getId()));
        return returnDesc;
    }

    @Override
    public void saveProjectText(StudentIdentifier studentIdentifier, String text) {

        ProjectDescription desc = getProjectbyStudent(studentIdentifier);
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
}
