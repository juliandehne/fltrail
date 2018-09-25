package unipotsdam.gf.core.management;

import unipotsdam.gf.core.management.project.Project;
import unipotsdam.gf.core.management.user.User;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/context")
@ManagedBean
public class ContextDataView {

    @Inject
    private Management iManagement;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/full")
    public ContextDataOutput getContextData(@QueryParam("userToken") String userToken, @QueryParam("projectToken")
                                            String projectToken) {
        Project projectByToken = iManagement.getProjectByToken(projectToken);
        User user = iManagement.getUserByToken(userToken);

        return new ContextDataOutput(projectByToken, user);

    }
}
