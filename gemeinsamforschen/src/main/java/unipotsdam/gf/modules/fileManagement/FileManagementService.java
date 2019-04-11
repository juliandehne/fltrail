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
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;

public class FileManagementService {

    public static void saveFileAsPDFBLOBInDB(
            User user,
            Project project,
            InputStream inputStream,
            FormDataContentDisposition fileDetail,
            FileRole fileRole
    ) throws IOException, SQLException {
        InputStream in = getInputStreamFromFile(inputStream, fileDetail);
        PDFFile pdfFile = new PDFFile(in, fileDetail.getFileName(), fileRole);
        FileManagementDAO.saveInputStreamToDB(user, project, pdfFile);
    }

    private static InputStream getInputStreamFromFile(InputStream inputStream, FormDataContentDisposition fileDetail) throws IOException {
        InputStream result;
        try{
            XMLSlideShow ppt = new XMLSlideShow(OPCPackage.open(inputStream));
            Document document = FileManagementService.convertPPTXtoPDF(
                    ppt,
                    fileDetail.getFileName().substring(0,fileDetail.getFileName().lastIndexOf("."))
            );
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
            result = new ByteArrayInputStream(out.toByteArray());
        }catch (Exception e){
            result = inputStream;
            //parse inputStream to PDF Document
        }
        inputStream.close();
        return result;
    }

    static Document convertPPTXtoPDF(XMLSlideShow ppt, String fileName) throws IOException, DocumentException {
        //create a new pdf with name of file
        Document document = new Document();
        if(false){  //writes the pdf File onto disk. Just for Test-reasons.
            FileOutputStream out = new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/"+fileName+".pdf");
            PdfWriter.getInstance(document, out);
        }
        PdfPTable table = new PdfPTable(1);

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
        return document;
    }
}
