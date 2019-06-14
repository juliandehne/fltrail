package unipotsdam.gf.modules.fileManagement;

import com.itextpdf.text.DocumentException;
import com.itextpdf.tool.xml.exceptions.CssResolverException;
import org.apache.catalina.manager.util.SessionUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.PeerAssessmentProcess;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Path("/fileStorage")
public class FileManagementView {

    @Inject
    private FileManagementService fileManagementService;

    @Inject
    private PeerAssessmentProcess assessmentProcess;

    @Inject
    private GFContexts gfContexts;

    @POST
    @Path("/{fileRole}/projectName/{projectName}")
    @Produces("application/json")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@Context HttpServletRequest req, @PathParam("projectName") String projectName,
                               @PathParam("fileRole") FileRole fileRole,
                               @FormDataParam("file") InputStream inputStream, @FormDataParam("file") FormDataContentDisposition fileDetail
    ) throws IOException, CssResolverException, DocumentException {
        fileManagementService.uploadFile1(req, projectName, fileRole, inputStream, fileDetail);
        assessmentProcess.fileHasBeenUploaded(fileRole, gfContexts.getUserFromSession(req), new Project(projectName));
        return Response.ok().build();
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
        responseBuilder.header("Content-Disposition", "attachment; filename=\"" + fileName);
        return responseBuilder.build();
    }

    @GET
    @Path("/listOfFiles/projectName/{projectName}")
    @Produces("application/json")
    public Map<String, String> getListOfFiles(@Context HttpServletRequest req, @PathParam("projectName") String projectName) throws IOException {
        return fileManagementService.getListOfFiles(req, projectName);
    }

    @DELETE
    @Path("/delete/fileLocation/{fileLocation}")
    public Response deleteFile(@PathParam("fileLocation") String fileLocation) throws IOException {
        fileManagementService.deleteFile(fileLocation);
        //Respond that everything worked out
        return Response.ok("Data deletion successfull").build();
    }
}