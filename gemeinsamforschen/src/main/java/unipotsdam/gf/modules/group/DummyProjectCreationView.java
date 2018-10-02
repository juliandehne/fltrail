package unipotsdam.gf.modules.group;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dummy/project/")
public class DummyProjectCreationView {

    @Inject
    DummyProjectCreationService dummyProjectCreationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createDummyProject() {

        boolean success = dummyProjectCreationService.createExampleProject();

        Response response;
        if (success) {
            response = Response.ok("Dummy-Project created").build();
        } else {
            response = Response.serverError().build();
        }

        return response;
    }


}
