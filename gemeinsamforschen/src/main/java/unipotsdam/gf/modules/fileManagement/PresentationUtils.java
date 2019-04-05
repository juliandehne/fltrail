package unipotsdam.gf.modules.fileManagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;

public class PresentationUtils {
    public static void main(String[] args) throws DocumentException, InvalidFormatException, IOException {
        //get the pptx from user
        String fileName = "multiDimensions";
        FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/" + fileName + ".pptx");
        XMLSlideShow ppt = new XMLSlideShow(OPCPackage.open(inputStream));
        inputStream.close();
        //convert pptx to PDF
        Document document = FileManagementService.convertPPTXtoPDF(ppt, fileName);

        //save pdf as blob in db

        //show upload in group

        //download pdf again

    }
}
