package unipotsdam.gf.modules.fileManagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.exceptions.CssResolverException;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.codehaus.plexus.util.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

public class FileManagementService {

    private static final String FOLDER_NAME = "userFilesFLTrail/";

    @Inject
    private FileManagementDAO fileManagementDAO;

    static File getPDFFile(String fileLocation) {
        return new File(FOLDER_NAME + fileLocation);
    }

    void saveFileAsPDF(
            User user,
            Project project,
            InputStream inputStream,
            FormDataContentDisposition fileDetail,
            FileRole fileRole,
            FileType fileType
    ) throws IOException, CssResolverException, DocumentException {
        String fileNameWithoutExtension = UUID.randomUUID().toString();
        FileUtils.mkdir(FOLDER_NAME);
        String fileName;
        switch (fileType) {
            case HTML:
                fileName = saveHTMLAsPDF(inputStream, fileNameWithoutExtension);
                break;
            case UNKNOWN:
            default:
                fileName = getDocumentFromFile(inputStream, fileNameWithoutExtension);
                break;
        }
        //writePDFFileOnFileSystem(pdfFile);
        fileManagementDAO.writePDFMetaToDB(user, project, fileName, fileRole, fileDetail.getFileName());
    }

    public void saveFileAsPDF(User user, Project project, String fileContent, FormDataContentDisposition fileDetail,
                              FileRole fileRole, FileType fileType) throws IOException, CssResolverException, DocumentException {
        fileContent = correctingTags(fileContent);
        InputStream inputStream = IOUtils.toInputStream(fileContent);
        saveFileAsPDF(user, project, inputStream, fileDetail, fileRole, fileType);

    }

    private String saveHTMLAsPDF(InputStream inputStream, String filenameWithoutExtension) throws IOException, DocumentException, CssResolverException {
        String fileName = filenameWithoutExtension + ".pdf";
        /*
        Document document = new Document();


        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();

            TODO: css is not applied correctly, that's why an indent, doesn't work, need fix
            example:
                HTML            PDF
                1.              1.
                2.              2.
                    a.          3.

        CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(false);
        cssResolver.addCssFile("https://cdnjs.cloudflare.com/ajax/libs/quill/1.3.6/quill.snow.css", true);
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        CssResolverPipeline pipeline = new CssResolverPipeline(cssResolver, new HtmlPipeline(htmlContext, new PdfWriterPipeline(document, writer)));
        XMLWorker worker = new XMLWorker(pipeline, true);
        XMLParser parser = new XMLParser(worker);
        parser.parse(inputStream);
         */
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStream);
        document.close();
        return fileName;
    }

    private String correctingTags(String fileContent) {
        return fileContent.replaceAll("<br>", "<br/>");
    }

    private String getDocumentFromFile(InputStream inputStream, String fileNameWithoutExtension) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String fileName;
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
            fileName = convertPPTXtoPDF(ppt, fileNameWithoutExtension);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
        }
        //if it was PDF-InputStream already, convert it to PDF-Document
        catch (Exception e) {
            fileName = convertInputStreamToPDF(inputStreamPDF, fileNameWithoutExtension);
        }

        inputStream.close();
        return fileName;
    }

    private String convertInputStreamToPDF(InputStream inputStream, String fileNameWithoutExtension) {
        String fileName = fileNameWithoutExtension + ".pdf";
        String qualifiedUploadFilePath = FOLDER_NAME + fileName;
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

    String convertPPTXtoPDF(XMLSlideShow ppt, String fileNameWithoutExtension) throws IOException, DocumentException {
        //create a new pdf with name of file
        Document document = new Document();
        PdfPTable table = new PdfPTable(1);

        //////////Wenn ich diese Funktion auslagere, funktioniert sie nicht mehr. Aber sie wird 2 mal verwendet =/
        String fileName = fileNameWithoutExtension + ".pdf";
        FileOutputStream out = new FileOutputStream(
                //to be found in "C:/dev/apache-tomcat-7.0.88-windows-x64/apache-tomcat-7.0.88/bin/userFiles"
                FOLDER_NAME + fileName
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

    Map<String, String> getListOfFiles(User user, Project project) {
        return fileManagementDAO.getListOfFiles(user, project);
    }

    void deleteFile(String fileLocation) {
        try {
            Path path = Paths.get("." + File.separator + FOLDER_NAME + File.separator + fileLocation);
            Files.delete(path);
        } catch (Exception e) {

        }
        fileManagementDAO.deleteMetaOfFile(fileLocation);

    }
}