package unipotsdam.gf.modules.assessment;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class AssessmentProgress {

    private int numberOfGroups;
    private int numberOfGroupPresentions;
    private int numberOfGroupReportsMissing;
    private int numberOfGroupsWithoutExternalAssessment;
    private int numberOfStudentsWithoutInternalAsssessment;

    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    public int getNumberOfGroupPresentions() {
        return numberOfGroupPresentions;
    }

    public int getNumberOfGroupReportsMissing() {
        return numberOfGroupReportsMissing;
    }

    public int getNumberOfGroupsWithoutExternalAssessment() {
        return numberOfGroupsWithoutExternalAssessment;
    }

    public int getNumberOfStudentsWithoutInternalAsssessment() {
        return numberOfStudentsWithoutInternalAsssessment;
    }

    public int getNumberOfGroupsWithoutPresentation() {
        return numberOfGroupsWithoutPresentation;
    }

    private int numberOfGroupsWithoutPresentation;

    AssessmentProgress() {
    }

    void setNumberOfGroupReportsMissing(int numberOfGroupReportsMissing) {
        this.numberOfGroupReportsMissing = numberOfGroupReportsMissing;
    }

    void setNumberOfGroupsWithoutExternalAssessment(int numberOfGroupsWithoutExternalAssessment) {
        this.numberOfGroupsWithoutExternalAssessment = numberOfGroupsWithoutExternalAssessment;
    }

    void setNumberOfStudentsWithoutInternalAsssessment(int numberOfStudentsWithoutInternalAsssessment) {
        this.numberOfStudentsWithoutInternalAsssessment = numberOfStudentsWithoutInternalAsssessment;
    }

    void setNumberOfGroupsWithoutPresentation(int numberOfGroupsWithoutPresentation) {
        this.numberOfGroupsWithoutPresentation = numberOfGroupsWithoutPresentation;
    }
}
