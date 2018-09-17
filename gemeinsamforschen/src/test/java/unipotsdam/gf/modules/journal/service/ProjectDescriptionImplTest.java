package unipotsdam.gf.modules.journal.service;

import org.junit.After;
import org.junit.Test;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.Link;
import unipotsdam.gf.modules.journal.model.ProjectDescription;
import unipotsdam.gf.modules.journal.model.dao.LinkDAO;
import unipotsdam.gf.modules.journal.model.dao.LinkDAOImpl;
import unipotsdam.gf.modules.journal.model.dao.ProjectDescriptionDAO;
import unipotsdam.gf.modules.journal.model.dao.ProjectDescriptionDAOImpl;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProjectDescriptionImplTest {

    private ProjectDescriptionService projectDescriptionService = new ProjectDescriptionImpl();

    private ProjectDescriptionDAO descriptionDAO = new ProjectDescriptionDAOImpl();
    private LinkDAO linkDAO = new LinkDAOImpl();
    private UserDAO userDAO = new UserDAO(new MysqlConnect());

    private String testId = "-1";
    private String testStudent = "testStudent";
    private String testDescription = "testDescription";
    private String testProject = "testProject";

    private String testProjectDescription = "testproject";
    private String testName = "testname";
    private String testLink = "https://www.test.de";
    private Link testLinkObj = new Link(testId, testProjectDescription, testName, testLink);

    private ArrayList<Link> linkArrayList = new ArrayList<>();

    private ProjectDescription testProjectDescriptionObj = new ProjectDescription(testId, testStudent, testDescription, testProject, linkArrayList);

    @After
    public void cleanUp() {
        StudentIdentifier deleteIdentifier = new StudentIdentifier(testProject, testStudent);
        ProjectDescription deleteDescription = descriptionDAO.getDescription(deleteIdentifier);
        descriptionDAO.deleteDescription(deleteIdentifier);

        if (deleteDescription != null) {
            ArrayList<Link> deleteLinks = linkDAO.getAllLinks(deleteDescription.getId());

            for (Link l : deleteLinks) {
                linkDAO.deleteLink(l.getId());
            }
        }
    }

    @Test
    public void getProjectbyStudent() {
        //NO description exists
        projectDescriptionService.getProjectByStudent(new StudentIdentifier(testProject, testStudent));

        ProjectDescription resProjectDescription = descriptionDAO.getDescription(new StudentIdentifier(testProject, testStudent));
        String id = resProjectDescription.getId();

        assertNotNull(resProjectDescription);
        assertEquals("Hier soll ein Turtorialtext stehen", resProjectDescription.getDescriptionMD());
        assertEquals(testStudent, resProjectDescription.getStudent().getStudentId());
        assertEquals(testProject, resProjectDescription.getStudent().getProjectId());

        //description exists
        resProjectDescription = descriptionDAO.getDescription(new StudentIdentifier(testProject, testStudent));

        assertNotNull(resProjectDescription);
        assertEquals(id, resProjectDescription.getId());
        assertEquals("Hier soll ein Turtorialtext stehen", resProjectDescription.getDescriptionMD());
        assertEquals(testStudent, resProjectDescription.getStudent().getStudentId());
        assertEquals(testProject, resProjectDescription.getStudent().getProjectId());

    }

    @Test
    public void getProjectbyId() {

        descriptionDAO.createDescription(testProjectDescriptionObj);
        String id = descriptionDAO.getDescription(new StudentIdentifier(testProject, testStudent)).getId();

        testLinkObj.setProjectDescription(id);
        linkDAO.addLink(testLinkObj);

        ProjectDescription resProjectDescription = projectDescriptionService.getProjectById(id);

        assertNotNull(resProjectDescription);
        assertEquals(testDescription, resProjectDescription.getDescriptionMD());
        assertEquals(testStudent, resProjectDescription.getStudent().getStudentId());
        assertEquals(testProject, resProjectDescription.getStudent().getProjectId());

        ArrayList<Link> links = resProjectDescription.getLinks();
        assertEquals(1, links.size());
        assertEquals(testLink, links.get(0).getLink());
        assertEquals(testName, links.get(0).getName());

    }

    @Test
    public void saveProjectText() {
        descriptionDAO.createDescription(testProjectDescriptionObj);

        projectDescriptionService.saveProjectText(new StudentIdentifier(testProject, testStudent), testName);

        ProjectDescription resProjectDescription = descriptionDAO.getDescription(new StudentIdentifier(testProject, testStudent));

        assertEquals(testName, resProjectDescription.getDescriptionMD());

    }

    @Test
    public void addLink() {
        descriptionDAO.createDescription(testProjectDescriptionObj);
        ProjectDescription resProjectDescription = descriptionDAO.getDescription(new StudentIdentifier(testProject, testStudent));

        projectDescriptionService.addLink(resProjectDescription.getId(), testLink, testName);

        ArrayList<Link> resLinks = linkDAO.getAllLinks(resProjectDescription.getId());

        assertEquals(1, resLinks.size());
        assertEquals(resProjectDescription.getId(), resLinks.get(0).getProjectDescription());
        assertEquals(testName, resLinks.get(0).getName());
        assertEquals(testLink, resLinks.get(0).getLink());

    }

    @Test
    public void deleteLink() {
        descriptionDAO.createDescription(testProjectDescriptionObj);
        ProjectDescription resProjectDescription = descriptionDAO.getDescription(new StudentIdentifier(testProject, testStudent));

        testLinkObj.setProjectDescription(resProjectDescription.getId());
        linkDAO.addLink(testLinkObj);

        ArrayList<Link> resLinks = linkDAO.getAllLinks(resProjectDescription.getId());

        assertEquals(1, resLinks.size());

        projectDescriptionService.deleteLink(resLinks.get(0).getId());

        resLinks = linkDAO.getAllLinks(resProjectDescription.getId());
        assertEquals(0, resLinks.size());

    }

    @Test
    public void closeDescription() {

        descriptionDAO.createDescription(testProjectDescriptionObj);

        ProjectDescription resDescription = descriptionDAO.getDescription(new StudentIdentifier(testProject, testStudent));

        assertTrue(resDescription.isOpen());

        projectDescriptionService.closeDescription(resDescription.getId());

        resDescription = descriptionDAO.getDescription(new StudentIdentifier(testProject, testStudent));

        assertFalse(resDescription.isOpen());
    }

    @Test
    public void checkIfAllDescriptionsClosed() {

        descriptionDAO.createDescription(testProjectDescriptionObj);

        Project project = new Project();
        project.setId(testProject);
        assertFalse(projectDescriptionService.checkIfAllDescriptionsClosed(project));

        ProjectDescription resDescription = descriptionDAO.getDescription(new StudentIdentifier(testProject, testStudent));

        descriptionDAO.closeDescription(resDescription.getId());

        assertTrue(projectDescriptionService.checkIfAllDescriptionsClosed(project));

    }

    @Test
    public void getOpenUserByProject() {
        User user = new User("Test", "Test", "test@test.de", true);
        String token = userDAO.getUserToken(user);

        Project project = new Project();
        project.setId(testProject);

        testProjectDescriptionObj.getStudent().setStudentId(token);
        descriptionDAO.createDescription(testProjectDescriptionObj);

        ArrayList<User> resultUser = projectDescriptionService.getOpenUserByProject(project);

        assertEquals(1, resultUser.size());
        assertEquals(user.getEmail(), resultUser.get(0).getId());

        StudentIdentifier delUser = new StudentIdentifier(testProject, token);
        descriptionDAO.deleteDescription(delUser);
    }
}