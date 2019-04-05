package unipotsdam.gf.modules.fileManagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManagementService {
    public static Document convertPPTXtoPDF(XMLSlideShow ppt, String fileName) throws IOException, DocumentException {
        //create a new pdf with name of file
        Document document = new Document();
        if(false){
            PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/"+fileName+".pdf"));
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
