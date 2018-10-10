package unipotsdam.gf.modules.project;

import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.modules.assessment.AssessmentMechanism;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.peer2peerfeedback.Category;

import java.util.HashMap;

// TODO implement
public class ProjectConfiguration {

    private HashMap<Phase, Boolean> phasesSelected;
    private HashMap<Category, Boolean> criteriaSelected;
    private HashMap<AssessmentMechanism, Boolean> assessmentMechanismSelected;
    private GroupFormationMechanism groupMechanismSelected;

    public ProjectConfiguration(
            HashMap<Phase, Boolean> phasesSelected,
            HashMap<Category, Boolean> criteriaSelected,
            HashMap<AssessmentMechanism, Boolean> assessmentMechanismSelected,
            GroupFormationMechanism groupMechanismSelected) {
        this.phasesSelected = phasesSelected;
        this.criteriaSelected = criteriaSelected;
        this.assessmentMechanismSelected = assessmentMechanismSelected;
        this.groupMechanismSelected = groupMechanismSelected;
    }

    public HashMap<Phase, Boolean> getPhasesSelected() {
        return phasesSelected;
    }

    public void setPhasesSelected(HashMap<Phase, Boolean> phasesSelected) {
        this.phasesSelected = phasesSelected;
    }

    public HashMap<Category, Boolean> getCriteriaSelected() {
        return criteriaSelected;
    }

    public void setCriteriaSelected(
            HashMap<Category, Boolean> criteriaSelected) {
        this.criteriaSelected = criteriaSelected;
    }

    public HashMap<AssessmentMechanism, Boolean> getAssessmentMechanismSelected() {
        return assessmentMechanismSelected;
    }

    public void setAssessmentMechanismSelected(
            HashMap<AssessmentMechanism, Boolean> assessmentMechanismSelected) {
        this.assessmentMechanismSelected = assessmentMechanismSelected;
    }


    public GroupFormationMechanism getGroupMechanismSelected() {
        return groupMechanismSelected;
    }

    public void setGroupMechanismSelected(
            GroupFormationMechanism groupMechanismSelected) {
        this.groupMechanismSelected = groupMechanismSelected;
    }


}
