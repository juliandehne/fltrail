package unipotsdam.gf.healthchecks;

import ch.vorburger.exec.ManagedProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.*;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.PoolingMysqlConnectImpl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

public class HealthChecks {

    //private static Boolean rocketChatAvailable = null;
    private final static Logger log = LoggerFactory.getLogger(HealthChecks.class);

    public static synchronized Boolean isRocketOnline() {
      /*  IConfig iConfig = null;
        if (FLTrailConfig.productionContext) {
            iConfig = new ProductionConfig();
        } else {
            iConfig = new TestConfig();
        }
        if (rocketChatAvailable == null) {
            Instant timeForCheck = Instant.now();
            rocketChatAvailable = hostAvailabilityCheck(iConfig.ROCKET_CHAT_LINK_0(), 80);
            Duration timePassed = Duration.between(Instant.now(), timeForCheck);
            log.trace("Rock: " + timePassed.toString());
        }*/
        return FLTrailConfig.rocketChatIsOnline;
    }

    private static Boolean compBaseAvailable = null;

    public static Boolean isCompBaseOnline() {
        if (compBaseAvailable == null) {
            Instant timeForCheck = Instant.now();
            Client client = ClientBuilder.newClient();
            Response response = client.target(
                    "http://fleckenroller.cs.uni-potsdam.de/app/competence-database-prod/api1/competences").request()
                    .get();
            compBaseAvailable = response.getStatus() == 200;
            Duration timePassed = Duration.between(Instant.now(), timeForCheck);
            log.trace("Comp: " + timePassed.toString());
        }
        return compBaseAvailable;
    }

    private static boolean hostAvailabilityCheck(String serveradress, int port) {

        try (Socket s = new Socket(serveradress, port)) {
            return true;
        } catch (IOException ex) {
            /* ignore */
        }
        return false;
    }

    private static Boolean mySQLAvailable = null;

    public static Boolean isMysqlOnline() {
       /* if (mySQLAvailable == null) {
            Instant timeForCheck = Instant.now();
            try {

                IConfig iConfig = null;
                if (FLTrailConfig.productionContext) {
                    iConfig = new ProductionConfig();
                } else {
                    iConfig = new TestConfig();
                }

                MysqlConnect mysqlConnect = new PoolingMysqlConnectImpl(iConfig);
                Connection connection = mysqlConnect.getConnection();
                Boolean mySQLAvailable = connection != null;
                if (mySQLAvailable) {
                    connection.close();
                }
                Duration timePassed = Duration.between(Instant.now(), timeForCheck);
                log.trace("MSQL: " + timePassed.toString());
                return mySQLAvailable;
            } catch (ManagedProcessException | SQLException e) {
                Duration timePassed = Duration.between(Instant.now(), timeForCheck);
                log.trace("MSQL: " + timePassed.toString());
                return mySQLAvailable;
            }
        }*/
        return true;
    }

    public static Boolean isGroupAlOnline() {
        boolean result = hostAvailabilityCheck(GeneralConfig.GROUPAl_BASE_URL, 12345);
        return result;
    }
}
