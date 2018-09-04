package unipotsdam.gf.modules.journal.model.dao;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.journal.model.Link;
import unipotsdam.gf.modules.journal.util.JournalUtils;

import java.util.ArrayList;
import java.util.UUID;

public class LinkDAOImpl implements LinkDAO{

    @Override
    public void addLink(Link link) {
        // create a new id
        String uuid = UUID.randomUUID().toString();
        while (JournalUtils.existsId(uuid,"links")) {
            uuid = UUID.randomUUID().toString();
        }

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "INSERT INTO links (`id`, `projecdesription`, `name`, `link`) VALUES (?,?,?,?);";
        connection.issueInsertOrDeleteStatement(request, uuid, link.getProjectDescription(),link.getName(),link.getLink());

        //close connection
        connection.close();
    }

    @Override
    public void deleteLink(String linkId) {
        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute requestxam
        String request = "DELETE FROM links WHERE id = ?;";
        connection.issueInsertOrDeleteStatement(request, linkId);

        // close connection
        connection.close();

    }

    @Override
    public Link getLink(String linkId) {
        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM links WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, linkId);

        if (rs.next()) {

            // save journal
            Link link = getLinkFromResultSet(rs);

            // close connection
            connection.close();

            return link;
        } else {

            // close connection
            connection.close();

            return null;
        }

    }

    @Override
    public ArrayList<Link> getAllLinks(String descriptionID) {
        ArrayList<Link> links = new ArrayList<>();

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT * FROM links WHERE projecdesription= ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, descriptionID);

        while (rs.next()) {
            links.add(getLinkFromResultSet(rs));
        }

        // close connection
        connection.close();

        return links;

    }

    private Link getLinkFromResultSet(VereinfachtesResultSet rs) {

        String id = rs.getString("id");
        String project = rs.getString("projecdesription");
        String name = rs.getString("name");
        String link = rs.getString("link");
        return new Link(id,project,name,link);
    }

}
