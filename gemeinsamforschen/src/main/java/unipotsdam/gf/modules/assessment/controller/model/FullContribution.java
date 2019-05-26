package unipotsdam.gf.modules.assessment.controller.model;

public class FullContribution extends Contribution{

    private ContributionCategory roleOfContribution;
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

    public FullContribution(ContributionCategory roleOfContribution, String textOfContribution) {
        this.roleOfContribution = roleOfContribution;
        this.textOfContribution = textOfContribution;
    }

    public FullContribution(String pathToFile, String nameOfFile, ContributionCategory roleOfContribution, String textOfContribution) {
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

    public ContributionCategory getRoleOfContribution() {
        return roleOfContribution;
    }

    public void setRoleOfContribution(ContributionCategory roleOfContribution) {
        this.roleOfContribution = roleOfContribution;
    }
}