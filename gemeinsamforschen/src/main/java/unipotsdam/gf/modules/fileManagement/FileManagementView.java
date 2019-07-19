package unipotsdam.gf.modules.fileManagement;

import com.itextpdf.text.DocumentException;
import com.itextpdf.tool.xml.exceptions.CssResolverException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.PeerAssessmentProcess;
import unipotsdam.gf.process.tasks.TaskName;
import unipotsdam.gf.session.GFContexts;
import unipotsdam.gf.session.Lock;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Path("/fileStorage")
public class FileManagementView {

    @Inject
    private FileManagementService fileManagementService;

    @Inject
    private PeerAssessmentProcess assessmentProcess;

    @Inject
    private GFContexts gfContexts;

    @Inject
    private Lock lock;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private UserDAO userDAO;

    @POST
    @Path("/{fileRole}/projectName/{projectName}")
    @Produces("application/json")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@Context HttpServletRequest req, @PathParam("projectName") String projectName,
                               @PathParam("fileRole") FileRole fileRole,
                               @FormDataParam("file") InputStream inputStream, @FormDataParam("file") FormDataContentDisposition fileDetail
    ) throws IOException, CssResolverException, DocumentException {
        String userEmail = gfContexts.getUserEmail(req);
        Group myGroup = groupDAO.getMyGroup(new User(userEmail), new Project(projectName));
        lock.deleteLockInDB(TaskName.UPLOAD_PRESENTATION, myGroup);
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
    public Response getListOfFiles(@Context HttpServletRequest req, @PathParam("projectName") String projectName) throws IOException {
        String userEmail = gfContexts.getUserEmail(req);
        User user = userDAO.getUserByEmail(userEmail);
        return Response.ok(fileManagementService.getListOfFiles(user, projectName)).build();
    }

    @DELETE
    @Path("/delete/fileLocation/{fileLocation}")
    public Response deleteFile(@PathParam("fileLocation") String fileLocation) {
        fileManagementService.deleteFile(fileLocation);
        //Respond that everything worked out
        return Response.ok("Data deletion successfull").build();
    }

    @GET
    @Path("/isOccupied/project/{projectName}/task/{taskName}")
    public Response isOccupied(@Context HttpServletRequest req,
                               @PathParam("projectName") String projectName,
                               @PathParam("taskName") TaskName taskName) throws IOException {
        String userEmail = gfContexts.getUserEmail(req);
        Group myGroup = groupDAO.getMyGroup(new User(userEmail), new Project(projectName));
        if (lock.lock(taskName, myGroup)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok().build();
    }
}