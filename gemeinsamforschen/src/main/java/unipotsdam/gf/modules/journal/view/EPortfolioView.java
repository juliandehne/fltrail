package unipotsdam.gf.modules.journal.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.modules.journal.model.EPortfolio;
import unipotsdam.gf.modules.journal.service.IJournalImpl;
import unipotsdam.gf.modules.journal.service.JournalService;
import unipotsdam.gf.modules.journal.service.JournalServiceImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * View for exporting the EPortfolio
 */

@Path("/eportfolio")
public class EPortfolioView {

    private final Logger log = LoggerFactory.getLogger(EPortfolioView.class);
    private final JournalService journalService = new JournalServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/pdf/{student}/{project}")
    public Response getPdf (@PathParam("student") String student, @PathParam("project") String project){

        IJournal iJournal = new IJournalImpl();

        EPortfolio ePortfolio =  iJournal.getPortfolio(project,student);

        byte[] pdf = iJournal.exportPortfolioToPdf(ePortfolio);

        return Response.ok(pdf).build();
    }


}
