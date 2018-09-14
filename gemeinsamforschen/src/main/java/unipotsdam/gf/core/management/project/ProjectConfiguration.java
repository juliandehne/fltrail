package unipotsdam.gf.core.management.project;

import unipotsdam.gf.core.states.model.ProjectPhase;
import unipotsdam.gf.modules.assessment.AssessmentMechanism;
import unipotsdam.gf.modules.groupfinding.GroupFormationMechanism;
import unipotsdam.gf.modules.peer2peerfeedback.Category;

import java.util.HashMap;

// TODO implement
public class ProjectConfiguration {

    private HashMap<ProjectPhase, Boolean> phasesSelected;
    private HashMap<Category, Boolean> criteriaSelected;
    private HashMap<AssessmentMechanism, Boolean> assessmentMechanismSelected;
    private HashMap<GroupFormationMechanism, Boolean> groupMechanismSelected;

    public ProjectConfiguration(
            HashMap<ProjectPhase, Boolean> phasesSelected,
            HashMap<Category, Boolean> criteriaSelected,
            HashMap<AssessmentMechanism, Boolean> assessmentMechanismSelected,
            HashMap<GroupFormationMechanism, Boolean> groupMechanismSelected) {
        this.phasesSelected = phasesSelected;
        this.criteriaSelected = criteriaSelected;
        this.assessmentMechanismSelected = assessmentMechanismSelected;
        this.groupMechanismSelected = groupMechanismSelected;
    }

    public HashMap<ProjectPhase, Boolean> getPhasesSelected() {
        return phasesSelected;
    }

    public void setPhasesSelected(HashMap<ProjectPhase, Boolean> phasesSelected) {
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


    public HashMap<GroupFormationMechanism, Boolean> getGroupMechanismSelected() {
        return groupMechanismSelected;
    }

    public void setGroupMechanismSelected(
            HashMap<GroupFormationMechanism, Boolean> groupMechanismSelected) {
        this.groupMechanismSelected = groupMechanismSelected;
    }


}
