package unipotsdam.gf.modules.general.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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

import javax.inject.Singleton;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Singleton
public class PDFGeneratorService {

    private final static String PATH = "./";

    public void generatePDFFromHTML(String fileContent, String filename) throws IOException, DocumentException, CssResolverException {
        Document document = new Document();
        fileContent = correctingTags(fileContent);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(PATH + filename));
        document.open();
        InputStream inputStream = IOUtils.toInputStream(fileContent);
        /*
            TODO: css is not applied correctly, that's why an indent
            example:
                HTML            PDF
                1.              1.
                2.              2.
                    a.          3.
         */
        //
        CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(false);
        cssResolver.addCssFile("https://cdnjs.cloudflare.com/ajax/libs/quill/1.3.6/quill.snow.css", true);
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        CssResolverPipeline pipeline = new CssResolverPipeline(cssResolver, new HtmlPipeline(htmlContext, new PdfWriterPipeline(document, writer)));
        XMLWorker worker = new XMLWorker(pipeline, true);
        XMLParser parser = new XMLParser(worker);
        parser.parse(inputStream);
        document.close();
    }

    private String correctingTags(String fileContent) {
        return fileContent.replaceAll("<br>", "<br/>");
    }
}
