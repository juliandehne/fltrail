package unipotsdam.gf.modules.journal.service;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.states.Constraints;
import unipotsdam.gf.modules.states.ConstraintsMessages;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Link;
import unipotsdam.gf.modules.journal.model.ProjectDescription;
import unipotsdam.gf.modules.journal.model.dao.LinkDAO;
import unipotsdam.gf.modules.journal.model.dao.LinkDAOImpl;
import unipotsdam.gf.modules.journal.model.dao.ProjectDescriptionDAO;
import unipotsdam.gf.modules.journal.model.dao.ProjectDescriptionDAOImpl;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjectDescriptionImpl implements ProjectDescriptionService {

    private final ProjectDescriptionDAO descriptionDAO = new ProjectDescriptionDAOImpl();
    private final LinkDAO linkDAO = new LinkDAOImpl();

    @Inject
    private UserDAO userDAO;

    @Override
    public ProjectDescription getProjectByStudent(StudentIdentifier userNameentifier) {

        //if no description exists (when page is opened for the first time), create a new one
        if (descriptionDAO.getDescription(userNameentifier) == null) {
            ProjectDescription description = new ProjectDescription("0", userNameentifier.getUserEmail(), "Hier soll ein Turtorialtext stehen", userNameentifier.getProjectName(), null);
            descriptionDAO.createDescription(description);
        }

        ProjectDescription returnDesc = descriptionDAO.getDescription(userNameentifier);
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
    public void saveProjectText(StudentIdentifier userNameentifier, String text) {

        ProjectDescription desc = getProjectByStudent(userNameentifier);
        desc.setDescription(text);
        descriptionDAO.updateDescription(desc);
    }

    @Override
    public void addLink(String project, String link, String name) {
        Link newLink = new Link(project, project, name, link);
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
    public Map<StudentIdentifier, ConstraintsMessages> checkIfAllDescriptionsClosed(Project project) {
        ArrayList<String> missingStudents = descriptionDAO.getOpenDescriptions(project);
        Map<StudentIdentifier, ConstraintsMessages> result = new HashMap<>();
        for (String userName: missingStudents) {
            StudentIdentifier student = new StudentIdentifier(project.getName(), userName);
            result.put(student, new ConstraintsMessages(Constraints.DescriptionsOpen, student));
        }
        return result;
    }

    @Override
    public ArrayList<User> getOpenUserByProject(Project project) {

        ArrayList<String> userEmail = descriptionDAO.getOpenDescriptions(project);
        ArrayList<User> users = new ArrayList<>();

        for (String id : userEmail) {
            users.add(userDAO.getUserByEmail(id));
        }
        return users;
    }
}
