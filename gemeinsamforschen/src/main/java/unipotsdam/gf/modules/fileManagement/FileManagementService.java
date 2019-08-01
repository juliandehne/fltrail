package unipotsdam.gf.modules.fileManagement;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.exceptions.NotImplementedException;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.codehaus.plexus.util.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.HtmlSerializer;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.submission.model.FullSubmissionPostRequest;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.session.GFContexts;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static unipotsdam.gf.config.FileConfig.FOLDER_NAME;

public class FileManagementService {


    @Inject
    private GFContexts gfContexts;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private FileManagementDAO fileManagementDAO;

    static File getPDFFile(String fileLocation) {
        return new File(FOLDER_NAME + fileLocation);
    }

    private String getFullPath(String fileName) {
        return FOLDER_NAME + fileName;
    }

    /**
     * you can save pptx, html and pdf files atm with this
     * @param user who uploads something
     * @param project of interest
     * @param inputStream the file that is about the be uploaded
     * @param fileDetail meta data about the file
     * @param fileRole DOSSIER, PRESENTATION or so
     * @param fileType PDF, html-text oder pptx atm
     * @throws IOException file not readable as html
     * @throws DocumentException converting file to pdf failed
     */
    private void saveFile(
            User user, Project project, InputStream inputStream, FormDataContentDisposition fileDetail,
            FileRole fileRole, FileType fileType) throws IOException, DocumentException {
        String fileNameWithoutExtension = UUID.randomUUID().toString();
        FileUtils.mkdir(FOLDER_NAME);
        String fileName;
        switch (fileType) {
            case HTML:
                fileName = saveHTMLAsPDF(inputStream, fileNameWithoutExtension);
                break;
            case UNKNOWN:
            case PDF:
            default:
                fileName = getDocumentFromFile(inputStream, fileNameWithoutExtension);
                break;
        }
        fileManagementDAO.writeFileMetaToDB(user, project, fileName, fileRole, fileDetail.getFileName());
    }

    public void saveStringAsPDF(User user, Project project, String fileContent, FormDataContentDisposition fileDetail,
                                FileRole fileRole, FileType fileType) throws IOException, DocumentException {
        if (fileType.equals(FileType.HTML)) {
            fileContent = cleanHTML(fileContent);
            //fileContent = manipulateIndentation(fileContent);
        }
        InputStream inputStream = IOUtils.toInputStream(fileContent);
        saveFile(user, project, inputStream, fileDetail, fileRole, fileType);
    }

    public void saveStringAsPDF(User user, Project project, FullSubmissionPostRequest fullSubmissionPostRequest) throws IOException, DocumentException {
        String fileName = fullSubmissionPostRequest.getHeader() + "_" + user.getEmail() + ".pdf";
        FileRole categoryString = fullSubmissionPostRequest.getFileRole();
        String content =  fullSubmissionPostRequest.getHtml();
        uploadFile(user, project, fileName, categoryString, content, FileType.HTML);
    }


    public void uploadFile(User user, Project project, String fileName, FileRole fileRole, String content, FileType
            fileType)
            throws IOException, DocumentException {
        FormDataContentDisposition.FormDataContentDispositionBuilder builder = FormDataContentDisposition.name
                (fileRole.name())
                .fileName(fileName);
        saveStringAsPDF(user, project, content , builder.build(), fileRole, fileType);
    }

    private String saveHTMLAsPDF(InputStream inputStream, String filenameWithoutExtension) throws IOException, DocumentException {
        String fileName = filenameWithoutExtension + ".pdf";
        String path = getFullPath(fileName);

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStream);
        document.close();
        return fileName;
    }

    private String cleanHTML(String fileContent) {
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode tagNode = htmlCleaner.clean(fileContent);
        HtmlSerializer htmlSerializer = new SimpleHtmlSerializer(htmlCleaner.getProperties());
        return htmlSerializer.getAsString(tagNode);
    }

    void manipulateIndentation(String fileContent) {
        /*
        todo:
            implementation hier
            -------------------
            - wenn text zurueckkommt, dann TextNode erzeugen, die text von vorheriger node beinhaltet, aber davor jeweilige aufzaehlungszeichen hinzufuegt
            - am ende alle <ol> und </ol> loeschen (wenn noetig)
         */
        org.jsoup.nodes.Document document = Jsoup.parse(fileContent);

        Elements elements = document.select("ol");
        elements.forEach(element -> {
            JsoupConverter converter = new JsoupConverter();
            String fullText = converter.convertElementsToTextNodes(element.childNodes());
            element.text(fullText);
        });
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

    private String convertPPTXtoPDF(XMLSlideShow ppt, String fileNameWithoutExtension) throws IOException,
            DocumentException {
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

    public List<ContributionStorage> getListOfFiles(User user, String projectName) {
        Project project = null;
        try {
            project = projectDAO.getProjectByName(projectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert project != null;
        boolean isAuthor = (user.getEmail().equals(project.getAuthorEmail()));
        return fileManagementDAO.getListOfFiles(user, project, isAuthor);
    }

    void deleteFile(String fileLocation) {
        try {
            Path path = Paths.get("." + File.separator + FOLDER_NAME + File.separator + fileLocation);
            Files.delete(path);
        } catch (Exception ignored) {

        }
        fileManagementDAO.deleteMetaOfFile(fileLocation);

    }

    private void uploadPPTX(
            User user, Project project, InputStream inputStream, FormDataContentDisposition fileDetail) throws IOException, DocumentException {
        //get the PDF Version of the InputStream and Write Meta into DB
        saveFile(user, project, inputStream, fileDetail, FileRole.PRESENTATION, FileType.UNKNOWN);
    }

    void uploadFile1(
            HttpServletRequest req, String projectName, FileRole fileRole, InputStream inputStream,
            FormDataContentDisposition fileDetail)
            throws IOException, DocumentException {
        String userEmail = gfContexts.getUserEmail(req);
        User user = userDAO.getUserByEmail(userEmail);
        Project project = null;
        try {
            project = projectDAO.getProjectByName(projectName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        uploadFile(fileRole, inputStream, fileDetail, user, project);
    }

    private void uploadFile(
            FileRole fileRole, InputStream inputStream, FormDataContentDisposition fileDetail, User user,
            Project project) throws IOException, DocumentException {
        deleteFiles(project, user, fileRole);

        switch (fileRole) {
            case PRESENTATION:
                uploadPPTX(user, project, inputStream, fileDetail);
                break;
            case DOSSIER:
                //this can just be used for PDFs and PPTX, so: todo replace this with docx interface
                saveFile(user, project, inputStream, fileDetail, FileRole.DOSSIER, FileType.UNKNOWN);
                break;
            case LEARNING_GOAL_RESULT:
                break;
            case EXTRA:
                // seems not to be implemented TODO @Axel
                throw new NotImplementedException();
            default:
                //uploadFile(user, project, inputStream, fileDetail);
                saveFile(user, project, inputStream, fileDetail, fileRole, FileType.PDF);
                break;
        }
    }

    //delete files from server and their meta data from DB
    public void deleteFiles(Project project, User user, FileRole fileRole) {
        List<String> fileLocations = fileManagementDAO.getFileLocation(project, user, fileRole);
        for (String fileLocation : fileLocations) {
            deleteFile(fileLocation);
        }
    }
}