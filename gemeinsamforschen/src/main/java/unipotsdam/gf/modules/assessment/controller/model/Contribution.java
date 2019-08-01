package unipotsdam.gf.modules.assessment.controller.model;

import unipotsdam.gf.modules.fileManagement.FileRole;

public class Contribution {
    private String pathToFile;
    private String nameOfFile;

    private FileRole roleOfContribution;

    public Contribution(String pathToFile, String nameOfFile, FileRole fileRole) {
        this.pathToFile = pathToFile;
        this.nameOfFile = nameOfFile;
        this.roleOfContribution = fileRole;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public FileRole getRoleOfContribution() {
        return roleOfContribution;
    }

    public void setRoleOfContribution(FileRole roleOfContribution) {
        this.roleOfContribution = roleOfContribution;
    }

    @Override
    public String toString() {
        return "Contribution{" +
                "pathToFile='" + pathToFile + '\'' +
                ", nameOfFile='" + nameOfFile + '\'' +
                '}';
    }
}
