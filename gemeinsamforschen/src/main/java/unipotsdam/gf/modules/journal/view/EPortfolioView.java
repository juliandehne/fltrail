package unipotsdam.gf.modules.journal.view;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.interfaces.IJournal;
import unipotsdam.gf.modules.journal.model.EPortfolio;
import unipotsdam.gf.modules.journal.service.IJournalImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

/**
 * View for exporting the EPortfolio
 */

@Path("/eportfolio")
public class EPortfolioView {

    private final Logger log = LoggerFactory.getLogger(EPortfolioView.class);


    /**
     * Returns the pdf file for export
     * TODO use owncloud when implemented
     * @param student ID of student
     * @param project ID of project
     * @return pdf
     */
    @GET
    @Produces("application/pdf")
    @Path("/pdf/{student}/{project}")
    public Response getPdfB (@PathParam("student") String student, @PathParam("project") String project){

        IJournal iJournal = new IJournalImpl();
        File file = new File("portfolio.pdf");
        Response.ResponseBuilder response = Response.ok(file);
        EPortfolio ePortfolio =  iJournal.getPortfolio(project,student);

        try {
            FileUtils.writeByteArrayToFile(file, iJournal.exportPortfolioToPdf(ePortfolio));
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.header("Content-Disposition",  "filename=restfile.pdf");
        return response.build();
    }

}
