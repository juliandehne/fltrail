package unipotsdam.gf.modules.fileManagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class FileManagementService {

    @Inject
    private FileManagementDAO fileManagementDAO;

    private static final String folderName = "userFilesFLTrail/";


    void saveFileAsPDF(
            User user,
            Project project,
            InputStream inputStream,
            FormDataContentDisposition fileDetail,
            FileRole fileRole
    ) throws IOException {
        String fileName = getDocumentFromFile(inputStream);

        //writePDFFileOnFileSystem(pdfFile);
        fileManagementDAO.writePDFMetaToDB(user, project, fileName, fileRole, fileDetail.getFileName());
    }

    private String getDocumentFromFile(InputStream inputStream) throws IOException {
        String fileName;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Read the Stream to copy it
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();

        // Open new InputStreams using the recorded bytes
        InputStream inputStreamPPTX = new ByteArrayInputStream(baos.toByteArray());
        InputStream inputStreamPDF = new ByteArrayInputStream(baos.toByteArray());

        //convert pptx-InputStream to pdf-Document
        try {
            Document document = new Document();
            XMLSlideShow ppt = new XMLSlideShow(OPCPackage.open(inputStreamPPTX));
            fileName = convertPPTXtoPDF(ppt);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
        }
        //if it was PDF-InputStream already, convert it to PDF-Document
        catch (Exception e) {
            fileName = convertInputStreamToPDF(inputStreamPDF);
        }

        inputStream.close();
        return fileName;
    }

    private String convertInputStreamToPDF(InputStream inputStream) {

        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + ".pdf";
        new File(folderName).mkdir();
        String qualifiedUploadFilePath = folderName + fileName;
        try (OutputStream outputStream = new FileOutputStream(new File(qualifiedUploadFilePath))) {

            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return fileName;
    }

    String convertPPTXtoPDF(XMLSlideShow ppt) throws IOException, DocumentException {
        //create a new pdf with name of file
        Document document = new Document();
        PdfPTable table = new PdfPTable(1);

        //////////Wenn ich diese Funktion auslagere, funktioniert sie nicht mehr. Aber sie wird 2 mal verwendet =/
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + ".pdf";
        new File(folderName).mkdir();
        FileOutputStream out = new FileOutputStream(
                //to be found in "C:/dev/apache-tomcat-7.0.88-windows-x64/apache-tomcat-7.0.88/bin/userFiles"
                folderName + fileName
        );
        PdfWriter.getInstance(document, out);
        /////////bis hier

        //create a image for each slide and save it as png
        Dimension pgsize = ppt.getPageSize();
        float scale = 1;
        int width = (int) (pgsize.width * scale);
        int height = (int) (pgsize.height * scale);
        for (XSLFSlide slide : ppt.getSlides()) {
            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width,
                    pgsize.height));
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            graphics.setColor(Color.white);
            graphics.clearRect(0, 0, width, height);
            graphics.scale(scale, scale);
            slide.draw(graphics);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            com.itextpdf.text.Image iTextImage = Image.getInstance(baos.toByteArray());
            document.open();
            iTextImage.setAbsolutePosition(0, 0);
            table.addCell(new PdfPCell(iTextImage, true));
        }
        document.add(table);
        document.close();
        return fileName;
    }

    public void downloadPDF(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment;filename=" + "testPDF.pdf");
        try {
            File f = new File("C://New folder//itext_Test.pdf");
            FileInputStream fis = new FileInputStream(f);
            DataOutputStream os = new DataOutputStream(response.getOutputStream());
            response.setHeader("Content-Length", String.valueOf(f.length()));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static File getPDFFile(String fileLocation) {
        return new File(folderName + fileLocation);
    }

    Map<String, String> getListOfFiles(User user, Project project){
        return fileManagementDAO.getListOfFiles(user, project);
    }
}