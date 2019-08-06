package unipotsdam.gf.modules.performance;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

@Path("/performance")
public class PerformanceView {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/stats")
    public HashMap<PerformanceCandidates, Long> getPerformanceStats() {
        return  PerformanceUtil.getStats();
    }
}
