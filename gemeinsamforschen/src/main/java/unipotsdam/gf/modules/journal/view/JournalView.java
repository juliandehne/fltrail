package unipotsdam.gf.modules.journal.view;

import unipotsdam.gf.modules.journal.model.Journal;
import unipotsdam.gf.modules.journal.model.JournalFilter;
import unipotsdam.gf.modules.journal.service.DummyJournalService;
import unipotsdam.gf.modules.journal.service.JournalService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * View for the learning journal
 *
 * TODO error handling, (maybe) method to change visibility
 */

@Path("/journal")
public class JournalView {

     JournalService journalService = new DummyJournalService();

    /**
     * Returns a specific Journal
     * @param id id of Journal
     * @return JSON of Journal
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Journal  getJournal (@PathParam("id") String id){

        Journal result = journalService.getJournal(id);

        return result;
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
    public ArrayList<Journal>  getAllJournals (@PathParam("student") String student, @PathParam("project") String project, @PathParam("filter") String filter){

         JournalFilter filt = (filter.equals("ALL")) ? JournalFilter.ALL:JournalFilter.OWN;

        ArrayList<Journal> result = journalService.getAllJournals(student,project,filt);

     return result;
    }

    /**
     * Returns all Journals for a student
     * @param student the requested student
     * @param project the requested project
     * @return Json of all Journals
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/journals/{student}/{project}")
    public ArrayList<Journal>  getAllJournals (@PathParam("student") String student, @PathParam("project") String project){

        ArrayList<Journal> result = journalService.getAllJournals(student,project);

        return result;
    }

    /**
     * Saves or edits a Journal
     * @param id id, -1 if new Journal
     * @param student owner of the Journal
     * @param project associated Project
     * @param text content of the Journal
     * @param visibility visibility of the Journal
     * @param category category of the Journal
     * @return TODO
     */

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/save")
    public String saveJournal(@FormParam("id") long id, @FormParam("student") String student,
                              @FormParam("project") String project, @FormParam("text") String text,
                              @FormParam("visibility") String visibility, @FormParam("category") String category) {

        journalService.saveJournal(id, student, project, text, visibility, category);

        return "ok";

    }

    /**
     * deletes a Journal
     * @param id id of the Journal
     * @return TODO
     */

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/delete/{id}")
    public String deleteJournal(@PathParam("id") long id) {

        journalService.deleteJournal(id);

        return "ok";
    }


}
