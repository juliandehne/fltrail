package unipotsdam.gf.modules.assessment.controller.model;

import java.nio.file.Path;

public class Contribution {
    private Path pathToFile;
    private String nameOfFile;

    @Override
    public String toString() {
        return "Contribution{" +
                "pathToFile=" + pathToFile +
                ", nameOfFile='" + nameOfFile + '\'' +
                '}';
    }

    public Contribution() {
    }

    public Contribution(Path pathToFile, String nameOfFile) {
        this.pathToFile = pathToFile;
        this.nameOfFile = nameOfFile;
    }

    public Path getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(Path pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }
}
