package unipotsdam.gf.modules.fileManagement;

import com.itextpdf.text.DocumentException;
import com.itextpdf.tool.xml.exceptions.CssResolverException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Path("/fileStorage")
public class fileManagementView {

    @Inject
    private GFContexts gfContexts;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private FileManagementService fileManagementService;

    @POST
    @Path("/presentation/projectName/{projectName}")
    @Produces("application/json")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPPTX(@Context HttpServletRequest req,@PathParam("projectName") String projectName,
            @FormDataParam("file") InputStream inputStream, @FormDataParam("file") FormDataContentDisposition fileDetail
    ) throws IOException, CssResolverException, DocumentException {
        //get the PDF Version of the InputStream and Write it in DB as BLOB
        String userEmail = gfContexts.getUserEmail(req);
        User user = userDAO.getUserByEmail(userEmail);
        Project project = projectDAO.getProjectByName(projectName);
        fileManagementService.saveFileAsPDF(user, project, inputStream, fileDetail, FileRole.PRESENTATION, FileType.UNKNOWN);

        //Respond that everything worked out
        return Response.ok("Data upload successfull").build();
    }

    // http://localhost:8080/Jersey-UP-DOWN-PDF-File/rest/fileservice/download/pdf
    @GET
    @Path("/download/fileLocation/{fileLocation}")
    @Produces("application/pdf")
    public Response downloadPdfFile(@PathParam("fileLocation") String fileLocation) {

        // set file (and path) to be download
        File file = FileManagementService.getPDFFile(fileLocation);

        Response.ResponseBuilder responseBuilder = Response.ok(file);

        String fileName = file.getName();
        responseBuilder.header("Content-Disposition", "attachment; filename=\""+fileName+".pdf\"");
        return responseBuilder.build();
    }

    @GET
    @Path("/listOfFiles/projectName/{projectName}")
    @Produces("application/json")
    public Map<String, String> getListOfFiles(@Context HttpServletRequest req, @PathParam("projectName") String projectName) throws IOException {
        String userEmail = gfContexts.getUserEmail(req);
        User user = userDAO.getUserByEmail(userEmail);
        Project project = projectDAO.getProjectByName(projectName);
        return fileManagementService.getListOfFiles(user, project);
    }

    @POST
    @Path("/delete/fileLocation/{fileLocation}")
    @Produces("application/json")
    public Response deleteFile(@Context HttpServletRequest req,@PathParam("fileLocation") String fileLocation) throws IOException {
        fileManagementService.deleteFile(fileLocation);
        //Respond that everything worked out
        return Response.ok("Data deletion successfull").build();
    }
}
