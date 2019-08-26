package unipotsdam.gf.modules.project;

import unipotsdam.gf.modules.assessment.AssessmentMechanism;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.phases.Phase;

import javax.inject.Inject;
import java.util.HashMap;

public class ProjectConfigurationDAO {

    @Inject
    MysqlConnect connect;

    @Inject
    SubmissionController submissionController;

    void persistProjectConfiguration(ProjectConfiguration projectConfiguration, Project project) {

        connect.connect();

        // persist Criteria
        HashMap<String, Boolean> criteriaSelected = projectConfiguration.getCriteriaSelected();
        for (String category : criteriaSelected.keySet()) {
            Boolean criteriumSelected = criteriaSelected.get(category);
            if (criteriumSelected != null && criteriumSelected) {
                String projectName = project.getName();
                String mysqlRequest = "insert INTO categoriesSelected (`projectName`,`categorySelected`) VALUES (?,?)";
                connect.issueInsertOrDeleteStatement(mysqlRequest, projectName, category);
            }
        }

        // persist Phases
        HashMap<Phase, Boolean> phasesSelected = projectConfiguration.getPhasesSelected();
        for (Phase phase : phasesSelected.keySet()) {
            Boolean projectPhaseSelected = phasesSelected.get(phase);
            if (projectPhaseSelected != null && projectPhaseSelected) {
                String mysqlRequest = "insert INTO phasesSelected (`projectName`,`phaseSelected`) VALUES (?,?)";
                connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), phase.name());
            }
        }

        // persist GroupFinding
        GroupFormationMechanism groupFindingMechanism =
                projectConfiguration.getGroupMechanismSelected();

        if (groupFindingMechanism != null) {
            String mysqlRequest =
                    "insert INTO groupfindingMechanismSelected (`projectName`,`gfmSelected`) VALUES (?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), groupFindingMechanism.name());
        }


        // persist assessmentMechanismSelected
        AssessmentMechanism assessmentMechanismsSelected =
                projectConfiguration.getAssessmentMechanismSelected();

        if (assessmentMechanismsSelected != null) {
            String mysqlRequest =
                    "insert INTO assessmentMechanismSelected (`projectName`,`amSelected`) VALUES (?,?)";
            connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), assessmentMechanismsSelected.name());
        }
        connect.close();
    }

    ProjectConfiguration loadProjectConfiguration(Project project) {
        connect.connect();


        HashMap<Phase, Boolean> projectPhasesSelected =
                getSelectionFromTable(connect, Phase.class, project, "phasesSelected");

        HashMap<String, Boolean> categorySelected = new HashMap<>();
        for (String category : submissionController.getAnnotationCategoriesWithConnection(project)) {
            categorySelected.put(category, true);
        }
        AssessmentMechanism asmSelected = AssessmentMechanism.PEER_ASSESSMENT;
        HashMap<AssessmentMechanism, Boolean> aMBHM = getSelectionFromTable(connect, AssessmentMechanism.class, project, "assessmentMechanismSelected");
        for (AssessmentMechanism am : aMBHM.keySet()){
            asmSelected =am;
        }
        HashMap<GroupFormationMechanism, Boolean> groupfindingMechanismSelected =
                getSelectionFromTable(connect, GroupFormationMechanism.class, project, "groupfindingMechanismSelected");


        GroupFormationMechanism gfmSelected = null;

        for (GroupFormationMechanism groupFormationMechanism : groupfindingMechanismSelected.keySet()) {
            if (groupfindingMechanismSelected.get(groupFormationMechanism)) {
                gfmSelected = groupFormationMechanism;
            }
        }
        connect.close();

        return new ProjectConfiguration(projectPhasesSelected, categorySelected,
                asmSelected, gfmSelected);
    }

    /**
     * this looks like magic but it is just a hack loading all the config tables by taking advantage
     * of the fact that they all have two columns with the second being the selected attribute and the first being the
     * project id
     *
     * @param connect connection to sql db
     * @param selectionclass phase, category, groupFindingMechanism or assessmentMechanism
     * @param project of interest
     * @param table table name of selected class from above
     * @param <T> Type of selected class from above
     * @return all selected elements of class T of project in db
     */
    private <T extends Enum<T>> HashMap<T, Boolean> getSelectionFromTable(
            MysqlConnect connect, Class<T> selectionclass, Project project, String table) {
        // get phasesSelected
        String id = project.getName();
        String mysqlRequest = "SELECT * FROM " + table + " where projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, id);
        HashMap<T, Boolean> projectPhaseBoolean = new HashMap<>();
        while (!vereinfachtesResultSet.isLast()) {
            boolean next = vereinfachtesResultSet.next();
            if (next) {
                String phaseSelected = vereinfachtesResultSet.getObject(2).toString();
                T phaseSelected1 = Enum.valueOf(selectionclass, phaseSelected);
                projectPhaseBoolean.put(phaseSelected1, true);
            } else {
                break;
            }
        }
        return projectPhaseBoolean;
    }
}
