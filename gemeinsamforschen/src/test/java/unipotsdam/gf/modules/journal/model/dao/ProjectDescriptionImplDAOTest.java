package unipotsdam.gf.modules.journal.model.dao;

import org.junit.Test;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.journal.model.ProjectDescription;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProjectDescriptionImplDAOTest {

    private final ProjectDescriptionDAO descriptionDAO = new ProjectDescriptionDAOImpl();
    private final MysqlConnect connection = new MysqlConnect();

    private final String testId = "-1";
    private final String testStudent = "testStudent";
    private final String testDescription = "testDescription";
    private final String testProjekt = "testProjekt";

    private final ProjectDescription testProjectDescription = new ProjectDescription(testId, testStudent, testDescription, testProjekt, null /*Links are added in Service*/);

    @Test
    public void createDescription() {

        connection.connect();

        descriptionDAO.createDescription(testProjectDescription);

        ArrayList<ProjectDescription> resultProjectDescriptions = getProjectDescription();

        //should be 1
        assertEquals(1, resultProjectDescriptions.size());

        ProjectDescription result = resultProjectDescriptions.get(0);

        //check data
        assertFalse(testId.equals(result.getId()));
        assertEquals(testDescription, result.getDescriptionMD());
        assertEquals(testStudent, result.getStudent().getUserEmail());
        assertEquals(testProjekt, result.getStudent().getProjectName());

        cleanup(result.getId());
        connection.close();
    }

    @Test
    public void updateDescription() {

        connection.connect();

        ProjectDescription updateDescription = testProjectDescription;

        create(updateDescription);

        updateDescription.setDescription(testDescription + testDescription);

        descriptionDAO.updateDescription(updateDescription);

        ArrayList<ProjectDescription> resultProjectDescriptions = getProjectDescription();

        //should be 1
        assertEquals(1, resultProjectDescriptions.size());

        ProjectDescription result = resultProjectDescriptions.get(0);

        //check data
        assertEquals(testId, result.getId());
        assertEquals(testDescription + testDescription, result.getDescriptionMD());

        cleanup(testId);
        connection.close();

    }

    @Test
    public void getDescriptionSI() {
        connection.connect();

        create(testProjectDescription);

        ProjectDescription result = descriptionDAO.getDescription(new StudentIdentifier(testProjekt, testStudent));

        assertEquals(testId, result.getId());
        assertEquals(testDescription, result.getDescriptionMD());
        assertEquals(testStudent, result.getStudent().getUserEmail());
        assertEquals(testProjekt, result.getStudent().getProjectName());

        cleanup(result.getId());
        connection.close();
    }

    @Test
    public void getDescriptionID() {
        connection.connect();

        create(testProjectDescription);

        ProjectDescription result = descriptionDAO.getDescription(testId);

        assertEquals(testId, result.getId());
        assertEquals(testDescription, result.getDescriptionMD());
        assertEquals(testStudent, result.getStudent().getUserEmail());
        assertEquals(testProjekt, result.getStudent().getProjectName());

        cleanup(result.getId());
        connection.close();
    }

    @Test
    public void deleteDescription() {
        connection.connect();

        create(testProjectDescription);

        ArrayList<ProjectDescription> resultDescriptions = getProjectDescription();

        //should be 1
        assertEquals(1, resultDescriptions.size());

        descriptionDAO.deleteDescription(new StudentIdentifier(testProjekt, testStudent));

        resultDescriptions = getProjectDescription();

        //should be 0
        assertEquals(0, resultDescriptions.size());

        connection.close();
    }

    @Test
    public void closeDescription() {
        connection.connect();

        create(testProjectDescription);

        ArrayList<ProjectDescription> resultDescriptions = getProjectDescription();

        //should be 1
        assertEquals(1, resultDescriptions.size());
        assertTrue(resultDescriptions.get(0).isOpen());

        descriptionDAO.closeDescription(testId);

        resultDescriptions = getProjectDescription();

        //should be 1
        assertEquals(1, resultDescriptions.size());
        assertFalse(resultDescriptions.get(0).isOpen());

        connection.close();
    }

    @Test
    public void getOpenDescriptions() {
        connection.connect();

        ProjectDescription openDescription = testProjectDescription;
        create(openDescription);
        openDescription.setId("-2");
        create(openDescription);
        openDescription.setId("-3");
        openDescription.setOpen(false);
        create(openDescription);

        Project project = new Project();
        project.setId(testProjekt);

        ArrayList<String> resultDescriptions = descriptionDAO.getOpenDescriptions(project);
        assertEquals(2, resultDescriptions.size());

        cleanup("-1", "-2", "-3");
        connection.close();
    }


    //Utility
    private ArrayList<ProjectDescription> getProjectDescription() {
        String request = "SELECT * FROM projectdescription WHERE projectName=?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, testProjekt);

        ArrayList<ProjectDescription> resultDescriptions = new ArrayList<>();
        while (rs.next()) {
            resultDescriptions.add(getDescriptionFromResultSet(rs));
        }
        return resultDescriptions;
    }

    private void create(ProjectDescription projectDescription) {
        String request = "INSERT INTO projectdescription(`id`, `userName`, `projectName`, `text`, `open`) VALUES (?,?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, projectDescription.getId(), projectDescription.getStudent().getUserEmail(), projectDescription.getStudent().getProjectName(), projectDescription.getDescriptionMD(), projectDescription.isOpen());
    }

    private void cleanup(String... ids) {
        for (String id : ids) {
            String request = "DELETE FROM projectdescription WHERE id = ? ;";
            connection.issueInsertOrDeleteStatement(request, id);
        }
    }

    private ProjectDescription getDescriptionFromResultSet(VereinfachtesResultSet rs) {
        String id = rs.getString("id");
        long timestamp = rs.getTimestamp(2).getTime();
        String author = rs.getString("userName");
        String project = rs.getString("projectName");
        String text = rs.getString("text");
        boolean open = rs.getBoolean("open");

        return new ProjectDescription(id, author, text, project, new ArrayList<>(), timestamp, open);
    }

}
