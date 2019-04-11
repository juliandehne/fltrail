package unipotsdam.gf.modules.fileManagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PresentationUtils {
    public static void main(String[] args) throws DocumentException, InvalidFormatException, IOException {
        //get the pptx from user
        String fileName = "multiDimensions";
        FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/" + fileName + ".pptx");
        XMLSlideShow ppt = new XMLSlideShow(OPCPackage.open(inputStream));
        inputStream.close();

        FileManagementService.convertPPTXtoPDF(ppt,fileName);

        /* definetly works
        Dimension pgsize = ppt.getPageSize();
        float scale = 1;
        int width = (int) (pgsize.width * scale);
        int height = (int) (pgsize.height * scale);

        int i = 1;
        int totalSlides = ppt.getSlides().size();

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
            FileOutputStream out = new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/" +fileName+i+".png");
            javax.imageio.ImageIO.write(img, "png", out);
            out.close();
            i++;
        }

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/" +fileName+".pdf"));
        PdfPTable table = new PdfPTable(1);


        for(int j = 1; j<=totalSlides; j++){
            com.itextpdf.text.Image slideImage = Image.getInstance(System.getProperty("user.dir") + "/src/main/resources/" +fileName+j+".png");

            document.setPageSize(new Rectangle(slideImage.getWidth(), slideImage.getHeight()));
            document.open();
            slideImage.setAbsolutePosition(0, 0);

            table.addCell(new PdfPCell(slideImage, true));

        }
        document.add(table);
        document.close();
        */

        //save pdf as blob in db

        //show upload in group

        //download pdf again

    }
}
