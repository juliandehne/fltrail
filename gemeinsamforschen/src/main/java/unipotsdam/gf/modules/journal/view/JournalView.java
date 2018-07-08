package unipotsdam.gf.modules.journal.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.JournalFilter;
import unipotsdam.gf.modules.journal.service.JournalService;
import unipotsdam.gf.modules.journal.service.JournalServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * View for the learning journal
 *
 * TODO error handling, (maybe) method to change visibility
 */

@Path("/journal")
public class JournalView {

    private final Logger log = LoggerFactory.getLogger(JournalView.class);
    private final JournalService journalService = new JournalServiceImpl();

    /**
     * Returns a specific Journal
     * @param id id of Journal
     * @return JSON of Journal
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getJournal (@PathParam("id") String id){

        log.debug(">>> getJournal: id=" + id );

        Journal result = journalService.getJournal(id);

        log.debug("<<< getJournal: result=" + result.toString());

        return Response.ok(result).build();
    }

    /**
     * Returns all Journals for a student, filtered whether only own or all Journals are requested
     * @param student the requested student
     * @param project the requested project
     * @param filter OWN or ALL
     * @return Json of all Journals
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/journals/{student}/{project}/{filter}")
    public Response getAllJournals (@PathParam("student") String student, @PathParam("project") String project, @PathParam("filter") String filter){

        log.debug(">>> getJournals: student=" + student + " project=" + project +" filter="  + filter  );

        JournalFilter filt = (filter.equals("ALL")) ? JournalFilter.ALL:JournalFilter.OWN;
        ArrayList<Journal> result = journalService.getAllJournals(student,project,filt);

        log.debug(">>> getJournals: size=" + result.size());

        return Response.ok(result).build();
    }


    /**
     * Saves or edits a Journal
     * @param id id, -1 if new Journal
     * @param student owner of the Journal
     * @param project associated Project
     * @param text content of the Journal
     * @param visibility visibility of the Journal
     * @param category category of the Journal
     * @return Empty Response
     */

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/save")
    public Response saveJournal(@FormParam("id") String id, @FormParam("student") String student,
                                @FormParam("project") String project, @FormParam("text") String text,
                                @FormParam("visibility") String visibility, @FormParam("category") String category) {

        log.debug(">>> createJournal");

        journalService.saveJournal(id, student, project, text, visibility, category);

        //TODO token
        URI location;
        try {
            location = new URI("../pages/eportfolio.jsp?token=test");
            log.debug("<<< createJournal: redirect to " + location.toString());
            return Response.temporaryRedirect(location).build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.debug("createJournal: redirect failed");
        }

        log.debug("<<< createJournal");

        return Response.ok().build();

    }

    /**
     * deletes a Journal
     * @param id id of the Journal
     * @return Empty Response
     */

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/delete/{id}")
    public Response deleteJournal(@PathParam("id") String id) {

        log.debug(">>> deleteJournal: id=" + id);

        journalService.deleteJournal(id);

        log.debug("<<< deleteJournal");

        return Response.ok().build();
    }

    //close journal
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/close")
    public Response closeJournal(String journal){
        log.debug(">>> closeJournal: " + journal);

        journalService.closeJournal(journal);
        //TODO token
        try {
            URI location = new URI("../pages/eportfolio.jsp?token=test");
            log.debug("<<< closeJournal: redirect to "  +location.toString());
            return Response.temporaryRedirect(location).build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.debug("closeJournal: redirect failed" );
        }

        log.debug("<<< closeJournal");
        return Response.ok().build();
    }

}
