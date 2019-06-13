package unipotsdam.gf.modules.assessment;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AssessmentProgress {

    private int numberOfGroups;
    private int numberOfGroupPresentions;
    private int numberOfGroupReports;
    private int numberOfGroupsWithoutExternalAssessment;
    private int numberOfStudentsWithoutInternalAsssessment;
    private int numberOfGroupsWithoutPresentation;


    public AssessmentProgress(
            int numberOfGroups, int numberOfGroupPresentions, int numberOfGroupReports,
            int numberOfGroupsWithoutExternalAssessment, int numberOfStudentsWithoutInternalAsssessment) {
        this.numberOfGroups = numberOfGroups;
        this.numberOfGroupPresentions = numberOfGroupPresentions;
        this.numberOfGroupReports = numberOfGroupReports;
        this.numberOfGroupsWithoutExternalAssessment = numberOfGroupsWithoutExternalAssessment;
        this.numberOfStudentsWithoutInternalAsssessment = numberOfStudentsWithoutInternalAsssessment;
    }

    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    public void setNumberOfGroups(int numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }

    public int getNumberOfGroupPresentions() {
        return numberOfGroupPresentions;
    }

    public void setNumberOfGroupPresentions(int numberOfGroupPresentions) {
        this.numberOfGroupPresentions = numberOfGroupPresentions;
    }

    public int getNumberOfGroupReports() {
        return numberOfGroupReports;
    }

    public void setNumberOfGroupReports(int numberOfGroupReports) {
        this.numberOfGroupReports = numberOfGroupReports;
    }

    public int getNumberOfGroupsWithoutExternalAssessment() {
        return numberOfGroupsWithoutExternalAssessment;
    }

    public void setNumberOfGroupsWithoutExternalAssessment(int numberOfGroupsWithoutExternalAssessment) {
        this.numberOfGroupsWithoutExternalAssessment = numberOfGroupsWithoutExternalAssessment;
    }

    public int getNumberOfStudentsWithoutInternalAsssessment() {
        return numberOfStudentsWithoutInternalAsssessment;
    }

    public void setNumberOfStudentsWithoutInternalAsssessment(int numberOfStudentsWithoutInternalAsssessment) {
        this.numberOfStudentsWithoutInternalAsssessment = numberOfStudentsWithoutInternalAsssessment;
    }



    public int getNumberOfGroupsWithoutPresentation() {
        return numberOfGroupsWithoutPresentation;
    }

    public void setNumberOfGroupsWithoutPresentation(int numberOfGroupsWithoutPresentation) {
        this.numberOfGroupsWithoutPresentation = numberOfGroupsWithoutPresentation;
    }

    public AssessmentProgress() {
    }


}
