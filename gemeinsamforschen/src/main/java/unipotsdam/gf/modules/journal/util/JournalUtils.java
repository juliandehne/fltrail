package unipotsdam.gf.modules.journal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.modules.journal.model.Visibility;
import unipotsdam.gf.modules.peer2peerfeedback.Category;

/**
 * Utility class for Journal and Project description
 */
public class JournalUtils {

    public static final Logger log = LoggerFactory.getLogger(JournalUtils.class);

    /**
     * Coverts a string to enum category
     *
     * @param category string
     * @return category, TITLE if string does not match
     */
    public static Category stringToCategory(String category) {

        Category c;

        // If String does not match enum IllegalArgumentException
        try {
            c = Category.valueOf(category);
        } catch (IllegalArgumentException e) {
            c = Category.TITEL;
            //TODO extra Category for fail?
            JournalUtils.log.debug("Illegal argument for visibility, default to TITLE");
        }
        return c;
    }

    /**
     * Converts a string to enum visibility
     *
     * @param visibility string
     * @return visibility, NONE if string does not match
     */
    public static Visibility stringToVisibility(String visibility) {
        Visibility v;

        // If String does not match enum IllegalArgumentException
        try {
            v = Visibility.valueOf(visibility);
        } catch (IllegalArgumentException e) {
            v = Visibility.MINE;
            JournalUtils.log.debug("Illegal argument for visibility, default to MINE");
        }
        return v;
    }

    /**
     * Checks if uuid ist used
     *
     * @param id uuid
     * @return true if free
     */
    public static boolean existsId(String id, String table) {

        // establish connection
        MysqlConnect connection = new MysqlConnect();
        connection.connect();

        // build and execute request
        String request = "SELECT COUNT(*) > 0 AS `exists` FROM " + table + " WHERE id = ?;";
        VereinfachtesResultSet rs = connection.issueSelectStatement(request, id);
        JournalUtils.log.debug("query: " + rs.toString());
        if (rs.next()) {
            // save the response
            int count = rs.getInt("exists");

            // close connection
            connection.close();

            // return true if we found the id
            return count >= 1;
        }

        // something happened
        return true;

    }
}
