package unipotsdam.gf.modules.assessment.controller.model;

import java.nio.file.Path;

public class FullContribution extends Contribution{

    private ContributionCategories roleOfContribution;
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

    public ContributionCategories getRoleOfContribution() {
        return roleOfContribution;
    }

    public void setRoleOfContribution(ContributionCategories roleOfContribution) {
        this.roleOfContribution = roleOfContribution;
    }

    public String getTextOfContribution() {
        return textOfContribution;
    }

    public void setTextOfContribution(String textOfContribution) {
        this.textOfContribution = textOfContribution;
    }

    public FullContribution(ContributionCategories roleOfContribution, String textOfContribution) {
        this.roleOfContribution = roleOfContribution;
        this.textOfContribution = textOfContribution;
    }

    public FullContribution(Path pathToFile, String nameOfFile, ContributionCategories roleOfContribution, String textOfContribution) {
        super(pathToFile, nameOfFile);
        this.roleOfContribution = roleOfContribution;
        this.textOfContribution = textOfContribution;
    }
}
