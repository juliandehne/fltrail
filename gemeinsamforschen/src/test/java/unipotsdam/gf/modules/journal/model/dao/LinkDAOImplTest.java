package unipotsdam.gf.modules.journal.model.dao;

import org.junit.Test;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.journal.model.Link;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LinkDAOImplTest {

    private final MysqlConnect connection = new MysqlConnect();
    private final LinkDAO linkDAO = new LinkDAOImpl();

    private final String testId = "-1";
    private final String testProjectDescription = "testproject";
    private final String testName = "testname";
    private final String testLink = "https://www.test.de";
    private final Link testLinkObj = new Link(testId, testProjectDescription, testName, testLink);

    @Test
    public void addLink() {

        connection.connect();

        //add Link
        Link addLink = testLinkObj;

        linkDAO.addLink(addLink);

        //check if added
        ArrayList<Link> resultLinks = getLinks(addLink.getProjectDescription());

        assertEquals(1, resultLinks.size());

        //check data
        Link resultLink = resultLinks.get(0);

        assertEquals(testLink, resultLink.getLink());
        assertEquals(testName, resultLink.getName());
        assertEquals(testProjectDescription, resultLink.getProjectDescription());

        assertFalse(testId.equals(resultLink.getId()));

        //cleanup
        cleanup(resultLink.getId());

        connection.close();
    }

    @Test
    public void deleteLink() {
        connection.connect();

        //add Link
        Link deleteLink = testLinkObj;

        create(deleteLink);


        //check if Link was created
        ArrayList<Link> reultLinks = getLinks(deleteLink.getProjectDescription());
        assertEquals(1, reultLinks.size());

        //delete link
        linkDAO.deleteLink(deleteLink.getId());

        //check if Link deleted
        reultLinks = getLinks(deleteLink.getProjectDescription());
        assertEquals(0, reultLinks.size());

        //cleanup
        cleanup(deleteLink.getId());

        connection.close();
    }

    @Test
    public void getLink() {
        connection.connect();

        //addLink
        Link getLink = testLinkObj;
        create(getLink);

        //get Link
        Link resultLink = linkDAO.getLink(getLink.getId());

        //check Data
        assertEquals(testLink, resultLink.getLink());
        assertEquals(testName, resultLink.getName());
        assertEquals(testProjectDescription, resultLink.getProjectDescription());

        //cleanup
        cleanup(getLink.getId());

        connection.close();

    }

    @Test
    public void getAllLinks() {
        connection.connect();

        //addLinks
        Link l = testLinkObj;
        create(l);
        l.setId("-2");
        l.setName("othername");
        create(l);
        l.setId("-3");
        l.setLink("404");
        create(l);

        //gettLinks
        ArrayList<Link> resultLinks = getLinks(testProjectDescription);

        //check
        assertEquals(3, resultLinks.size());

        //cleanup
        cleanup("-1", "-2", "-3");

        connection.close();
    }

    //Utility
    private void create(Link deleteLink) {
        String request = "INSERT INTO links (`id`, `projecdesription`, `name`, `link`) VALUES (?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, deleteLink.getId(), deleteLink.getProjectDescription(), deleteLink.getName(), deleteLink.getLink());
    }

    private void cleanup(String... addLinks) {

        for (String addLink : addLinks) {

            String request = "DELETE FROM links WHERE id = ?;";
            connection.issueInsertOrDeleteStatement(request, addLink);
        }
    }

    private Link getLinkFromResultSet(VereinfachtesResultSet rs) {

        String id = rs.getString("id");
        String project = rs.getString("projecdesription");
        String name = rs.getString("name");
        String link = rs.getString("link");
        return new Link(id, project, name, link);
    }

    private ArrayList<Link> getLinks(String addLink) {
        ArrayList<Link> resultLinks = new ArrayList<>();

        String request = "SELECT * FROM links WHERE projecdesription= ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, addLink);

        while (rs.next()) {
            resultLinks.add(getLinkFromResultSet(rs));
        }

        return resultLinks;
    }
}