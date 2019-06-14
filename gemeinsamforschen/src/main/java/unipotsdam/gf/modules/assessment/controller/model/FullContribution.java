package unipotsdam.gf.modules.assessment.controller.model;

import unipotsdam.gf.modules.fileManagement.FileRole;

public class FullContribution extends Contribution{

    private FileRole roleOfContribution;
    private String textOfContribution;

    public FullContribution() {
    }

    @Override
    public String toString() {
        return "FullContribution{" +
                "roleOfContribution=" + roleOfContribution +
                ", textOfContribution='" + textOfContribution + '\'' +
                '}';
    }

    public FullContribution(FileRole roleOfContribution, String textOfContribution) {
        this.roleOfContribution = roleOfContribution;
        this.textOfContribution = textOfContribution;
    }

    public FullContribution(String pathToFile, String nameOfFile, FileRole roleOfContribution, String textOfContribution) {
        super(pathToFile, nameOfFile);
        this.roleOfContribution = roleOfContribution;
        this.textOfContribution = textOfContribution;
    }

    public String getTextOfContribution() {
        return textOfContribution;
    }

    public void setTextOfContribution(String textOfContribution) {
        this.textOfContribution = textOfContribution;
    }

    public FileRole getRoleOfContribution() {
        return roleOfContribution;
    }

    public void setRoleOfContribution(FileRole roleOfContribution) {
        this.roleOfContribution = roleOfContribution;
    }
}