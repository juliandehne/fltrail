package unipotsdam.gf.healthchecks;

import unipotsdam.gf.mysql.IConnectionPoolUtility;
import unipotsdam.gf.mysql.PoolingMysqlConnectImpl;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

@Path("/system")
public class HealthChecksView {

    @Inject
    IConnectionPoolUtility connectionPoolUtility;

    @Path("/health")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public HealthData getHealth() throws SQLException, PropertyVetoException {
        /*Boolean rocketOnline = HealthChecks.isRocketOnline();
        Boolean compBaseOnline = HealthChecks.isCompBaseOnline();
        Boolean mysqlOnline = HealthChecks.isMysqlOnline();
        Boolean groupAlOnline = HealthChecks.isGroupAlOnline();*/
        //return new HealthData(compBaseOnline, rocketOnline, mysqlOnline, groupAlOnline);

        String mysqlConnectionStatus = connectionPoolUtility.getConnectionStatus();
        mysqlConnectionStatus += "connect vs close counter: " + PoolingMysqlConnectImpl.counter.get();
        return new HealthData(true, false, true, true, mysqlConnectionStatus);
    }
}
