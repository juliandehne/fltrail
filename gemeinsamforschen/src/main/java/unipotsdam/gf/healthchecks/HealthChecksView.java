package unipotsdam.gf.healthchecks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/system")
public class HealthChecksView {
    @Path("/health")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public HealthData getHealth() {
        Boolean rocketOnline = HealthChecks.isRocketOnline();
        Boolean compBaseOnline = HealthChecks.isCompBaseOnline();
        Boolean mysqlOnline = HealthChecks.isMysqlOnline();
        Boolean groupAlOnline = HealthChecks.isGroupAlOnline();
        HealthData data = new HealthData(rocketOnline, compBaseOnline, mysqlOnline, groupAlOnline);
        return data;
    }
}
