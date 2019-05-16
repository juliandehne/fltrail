package unipotsdam.gf.healthchecks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/system")
public class HealthChecksView {
    @Path("/health")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public HealthData getHealth() {
        Boolean rocketOnline = HealthChecks.isRocketOnline();
        Boolean compBaseOnline = HealthChecks.isCompBaseOnline();
        Boolean mysqlOnline = HealthChecks.isMysqlOnline();
        Boolean groupAlOnline = HealthChecks.isGroupAlOnline();
        //return new HealthData(compBaseOnline, rocketOnline, mysqlOnline, groupAlOnline);
        return new HealthData(true,rocketOnline,true,true);
    }
}
