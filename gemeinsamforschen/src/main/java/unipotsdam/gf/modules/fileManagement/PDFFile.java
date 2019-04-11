package unipotsdam.gf.modules.fileManagement;

import java.io.InputStream;

public class PDFFile {
    private InputStream BLOB;
    private String nameOfFile;
    private FileRole fileRole;

    public PDFFile(InputStream blob, String nameOfFile, FileRole fileRole) {
        BLOB = blob;
        this.nameOfFile = nameOfFile;
        this.fileRole = fileRole;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public InputStream getBLOB() {
        return BLOB;
    }

    public void setBLOB(InputStream BLOB) {
        this.BLOB = BLOB;
    }

    public FileRole getFileRole() {
        return fileRole;
    }

    public void setFileRole(FileRole fileRole) {
        this.fileRole = fileRole;
    }
}
