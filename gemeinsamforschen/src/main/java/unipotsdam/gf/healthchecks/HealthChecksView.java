package unipotsdam.gf.healthchecks;

import unipotsdam.gf.mysql.MysqlConnectImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/system")
public class HealthChecksView {
    @Path("/health")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public HealthData getHealth() throws SQLException {
        Boolean rocketOnline = HealthChecks.isRocketOnline();
        Boolean compBaseOnline = HealthChecks.isCompBaseOnline();
        Boolean mysqlOnline = HealthChecks.isMysqlOnline();
        Boolean groupAlOnline = HealthChecks.isGroupAlOnline();
        //return new HealthData(compBaseOnline, rocketOnline, mysqlOnline, groupAlOnline);
        String mysqlConnectionStatus = MysqlConnectImpl.getConnectionStatus();
        return new HealthData(true,false,true,true, mysqlConnectionStatus);
    }
}
