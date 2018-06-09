package unipotsdam.gf.modules.journal.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.service.DummyJournalService;
import unipotsdam.gf.modules.journal.service.JournalService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/journal")
public class JournalView {

    Logger log = LoggerFactory.getLogger(JournalView.class);
    JournalService journalService = new DummyJournalService();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Journal";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/journals/{student}/{project}")
    public Response getAllJournals(@PathParam("student") String student, @PathParam("project") String project) {

        log.debug(">> called getAllJournals(" + student + "," + project + ")");
        try {
            ArrayList<Journal> journals = journalService.getAllJournals(student, project);

            JournalListResponse result = new JournalListResponse();
            result.setJournals(journals);

            return Response.ok(new GenericEntity<JournalListResponse>(result) {
            }).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.noContent().build();
        }
    }
}