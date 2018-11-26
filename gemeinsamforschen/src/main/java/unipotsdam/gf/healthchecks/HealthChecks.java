package unipotsdam.gf.healthchecks;

import ch.vorburger.exec.ManagedProcessException;
import unipotsdam.gf.mysql.MysqlConnectImpl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class HealthChecks {
    public static Boolean isRocketOnline() {
        return hostAvailabilityCheck("fleckenroller.cs.uni-potsdam.de", 3000);
    }

    public static Boolean isCompBaseOnline() {
        Client client = ClientBuilder.newClient();
        Response response =
                client.target("http://fleckenroller.cs.uni-potsdam.de/app/competence-database-prod/api1/competences")
                        .request().get();
        return response.getStatus() == 200;
    }

    private static boolean hostAvailabilityCheck(String serveradress, int port) {

        try (Socket s = new Socket(serveradress, port)) {
            return true;
        } catch (IOException ex) {
            /* ignore */
        }
        return false;
    }

    public static Boolean isMysqlOnline() {
        MysqlConnectImpl mysqlConnect = new MysqlConnectImpl();
        try {
            Connection connection = mysqlConnect.getConnection();
            Boolean result =  connection != null;
            if (result) {
                connection.close();
            }
            return result;
        } catch (ManagedProcessException e) {
            return false;
        } catch (SQLException e) {
            return false;
        }
    }
}
