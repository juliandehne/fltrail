package unipotsdam.gf.modules.project;

import unipotsdam.gf.modules.annotation.model.Category;
import unipotsdam.gf.modules.assessment.AssessmentMechanism;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.process.phases.Phase;

import java.util.HashMap;

// TODO implement
public class ProjectConfiguration {

    private HashMap<Phase, Boolean> phasesSelected;
    private HashMap<Category, Boolean> criteriaSelected;
    private AssessmentMechanism assessmentMechanismSelected;
    private GroupFormationMechanism groupMechanismSelected;

    ProjectConfiguration(
            HashMap<Phase, Boolean> phasesSelected,
            HashMap<Category, Boolean> criteriaSelected,
            AssessmentMechanism assessmentMechanismSelected,
            GroupFormationMechanism groupMechanismSelected) {
        this.phasesSelected = phasesSelected;
        this.criteriaSelected = criteriaSelected;
        this.assessmentMechanismSelected = assessmentMechanismSelected;
        this.groupMechanismSelected = groupMechanismSelected;
    }

    public HashMap<Phase, Boolean> getPhasesSelected() {
        return phasesSelected;
    }

    public HashMap<Category, Boolean> getCriteriaSelected() {
        return criteriaSelected;
    }

    public AssessmentMechanism getAssessmentMechanismSelected() {
        return assessmentMechanismSelected;
    }

    public GroupFormationMechanism getGroupMechanismSelected() {
        return groupMechanismSelected;
    }
}
