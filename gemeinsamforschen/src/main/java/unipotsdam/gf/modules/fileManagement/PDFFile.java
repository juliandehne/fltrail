package unipotsdam.gf.modules.fileManagement;

import com.itextpdf.text.Document;

import java.io.InputStream;

public class PDFFile {
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    private Document document;
    private String nameOfFile;
    private FileRole fileRole;

    public PDFFile(Document document, String nameOfFile, FileRole fileRole) {
        this.document = document;
        this.nameOfFile = nameOfFile;
        this.fileRole = fileRole;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public FileRole getFileRole() {
        return fileRole;
    }

    public void setFileRole(FileRole fileRole) {
        this.fileRole = fileRole;
    }
}
