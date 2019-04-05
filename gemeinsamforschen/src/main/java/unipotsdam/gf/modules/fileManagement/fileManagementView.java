package unipotsdam.gf.modules.fileManagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Path("/fileStorage")
public class fileManagementView {

    @POST
    @Path("/presentation/fileName/{fileName}")
    @Produces("application/json")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadPPTX(
            //@FormDataParam("file") InputStream inputStream, @FormDataParam("file") FormDataContentDisposition fileDetail,
            FileInputStream inputStream,
            @PathParam("fileName") String fileName) throws IOException, InvalidFormatException, DocumentException {
        XMLSlideShow ppt = new XMLSlideShow(OPCPackage.open(inputStream));
        inputStream.close();
        //convert pptx to PDF
        Document document = FileManagementService.convertPPTXtoPDF(ppt, fileName);

        return null;
    }
}
